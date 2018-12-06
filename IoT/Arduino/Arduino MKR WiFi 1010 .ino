#include <string.h>

#include <Wire.h>
#include <SPI.h>
#include <DHT.h>
#include <OneWire.h>
#include <DallasTemperature.h>


#include <WiFiNINA.h>
#include <MQTT.h>

/* In which pins are my sensors plugged? */
#define LUM A0  // TEMT6000 Signal pin
#define TMP 4   // DHT22 Signal pin


/* We need a DHT object to address the sensor. */
DHT dht(TMP, DHT22); // pin: TMP, model: DHT22


/* We need objects to handle:
    1. WiFi connectivity
    2. MQTT messages
*/
WiFiClient wifi_client ;
MQTTClient mqtt_client ;

/* And associated variables to tell:
    1. which WiFi network to connect to
    2. what are the MQTT broket IP address and TCP port
*/
const char* wifi_ssid = "*******";
const char* wifi_password = "**********";

const char* mqtt_host = "1*************" ;
const uint16_t mqtt_port =  1883;

const char* mqtt_user = "********";
const char* mqtt_pass = "*********";

/* Some variables to handle measurements. */
int tmp ;
int lum ;
int hmdt ;
boolean first_time ;
uint32_t t0, t ;

/* 'topic' is the string representing the topic on which messages
    will be published.

   TODO: You need to adapt it by setting:
    1. 'surname' to your surname
    2. 'ID' to a unique ID chosen in the class with other students
*/
String surname = "Mike";
String roomID = "212" ;
String topic = "#";

/* Time between two sensings and values sent. */
#define DELTA_T 2000


/* ################################################################### */
void setup() {
  Serial.begin(1) ;  // monitoring via Serial is always nice when possible
  delay(100) ;
  Serial.println("Initializing...\n") ;

  pinMode(LED_BUILTIN, OUTPUT) ;
  digitalWrite(LED_BUILTIN, LOW) ;  // switch LED off

  dht.begin() ;

  WiFi.begin(wifi_ssid, wifi_password) ;
  mqtt_client.begin(mqtt_host, mqtt_port, wifi_client) ;
  mqtt_client.onMessage(callback) ;

  tmp = lum = hmdt = -1.0 ;

  first_time = true ;

  // Time begins now!
  t0 = t = millis() ;
}


/* ################################################################### */
void loop() {
  /* We try to connect to the broker and launch the client loop.
  */
  mqtt_client.loop() ;
  if (!mqtt_client.connected() ) {
    reconnect() ;
  }

  /* Values are read from sensors at fixed intervals of time.
  */
  // ===================================================
  t = millis() ;
  if ( first_time || (t - t0) >= DELTA_T ) {
    t0 = t ;
    first_time = false ;

    lum = getLum() ;
    tmp = getTmp() ;
    hmdt = getHmdt() ;

    sendValues() ;
    delay(10000);
  }
}


/* ------------------------------------------------------------------- */
double getLum()
{
  double acc = 0 ;
  uint8_t n_val ;

  for ( n_val = 0 ; n_val < 10 ; n_val++ ) {
    acc += analogRead(LUM) ;
    delay(5) ;
  }

  return acc / n_val ;
}


/* ------------------------------------------------------------------- */
double getTmp()
{
  return dht.readTemperature() ;
}


/* ------------------------------------------------------------------- */
double getHmdt()
{
  return dht.readHumidity() ;
}

/* ################################################################### */
/* This function handles the connection/reconnection to
   the MQTT broker.
   Each time a connection or reconnection is made, it
   publishes a message on the topic+"/HELLO" containing
   the number of milli seconds since uController start.
*/
void reconnect() {
  Serial.print("Connecting to ");
  Serial.print(wifi_ssid);
  Serial.print("\n") ;

  while (WiFi.status() != WL_CONNECTED) {
    digitalWrite(LED_BUILTIN, HIGH) ;
    delay(500);
    digitalWrite(LED_BUILTIN, LOW) ;
    Serial.print(".");
    delay(1000);
    WiFi.begin(wifi_ssid, wifi_password);
  }

  Serial.print("\n");
  Serial.print("WiFi connected\n");
  Serial.print("IP address: \n");
  Serial.print(WiFi.localIP());
  Serial.print("\n") ;


  Serial.print("MQTT: trying to connect to host ") ;
  Serial.print(mqtt_host) ;
  Serial.print(" on port ") ;
  Serial.print(mqtt_port) ;
  Serial.println(" ...") ;

  while (!mqtt_client.connect("001", mqtt_user, mqtt_pass) ) {
    digitalWrite(LED_BUILTIN, HIGH) ;
    delay(500);
    digitalWrite(LED_BUILTIN, LOW) ;
    Serial.print(".");
    delay(10000);
    mqtt_client.connect("001", mqtt_user, mqtt_pass);
  }
  Serial.println("MQTT: connected") ;
  Serial.print("MQTT: root topic is \"") ;
  Serial.print(topic) ;
  Serial.println("\"") ;

  mqtt_client.publish(String(topic + "/HELLO").c_str(), String(millis())) ;

  /* If you want to subscribe to topics, you have to do it here. */
}

/* ################################################################### */
/* This function handles the sending of all the values
   collected by the sensors.
   Values are sent on a regular basis (every DELTA_T_SEND_VALUES
   milliseconds).
*/
void sendValues() {
  if ( mqtt_client.connected() ) {
//    if ( lum != -1 )
//      mqtt_client.publish(String(topic + "/LUMI").c_str(), String(lum).c_str()) ;
//      Serial.print(String(topic + "/LUMI").c_str(), String(lum).c_str());
    if ( tmp != -1 ){
//      mqtt_client.publish(String(topic + "/temp").c_str(), String(tmp).c_str());
      mqtt_client.publish(String(topic + "/temp").c_str(), String("{\"room\":\"" + roomID + "\", \"temp\":\"" + tmp + "\", \"light\":\"" + lum + "\",\"hmdt\":\"" + hmdt + "\"}").c_str());
      Serial.println(tmp);}
//    if ( hmdt != -1 )
//      mqtt_client.publish(String(topic + "/HMDT").c_str(), String(hmdt).c_str()) ;
//      Serial.print(String(topic + "/HMDT").c_str(), String(hmdt).c_str());
  }
}

/* ################################################################### */
/* The main callback to listen to topics.
*/
void callback(String &intopic, String &payload)
{
  /* There's nothing to do here ... as long as the module
      cannot handle messages.
  */
  Serial.println("incoming: " + topic + " - " + payload);
}
