#include <Wire.h>
#include <SPI.h>
#include <DHT.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include <WiFiNINA.h>
#include <MQTT.h>
#include <string.h>

/* In which pins are my sensors plugged? */
#define LUM A0  // TEMT6000 Signal pin
#define TMP 4   // DHT22 Signal pin

/* We need a DHT object to address the sensor. */
DHT dht(TMP, DHT22); // pin: TMP, model: DHT22


/* We need objects to handle:
    1. WiFi connectivity
    2. MQTT messages
*/
WiFiClient wifi_client;
MQTTClient mqtt_client;

/* And associated variables to tell:
    1. which WiFi network to connect to
    2. what are the MQTT broket IP address and TCP port
*/
const char* wifi_ssid = "Mqtt";
const char* wifi_password = "KHBk32jh3kjhbksdjhbkjHBKJeh2";

const char* mqtt_host = "192.168.137.1" ;
const uint16_t mqtt_port =  1883;

const char* mqtt_user = "mqtt-test";
const char* mqtt_pass = "mqtt-test";

/* Some variables to handle measurements. */
int tmp ;
int lum ;
int hmdt ;
boolean first_time ;
uint32_t t0, t ;

/* 'topic' is the string representing the topic on which messages
    will be published.*/
String topic = "#";

/*Button*/
const int buttonPin = 6;     // the number of the pushbutton pin
const int ledPin =  13;
int buttonState = 0;

/*Mac*/
  byte mac[6];
  String macadress = "";
  int status = WL_IDLE_STATUS;

/*Time between two sensings and values sent.*/
#define DELTA_T 2000

/* ################################################################### */
void setup() {
  Serial.begin(1) ;  // monitoring via Serial is always nice when possible
  delay(100) ;
  Serial.println("Initializing...\n") ;

  pinMode(LED_BUILTIN, OUTPUT) ;
  digitalWrite(LED_BUILTIN, LOW) ;  // switch LED off

  dht.begin() ;

  status = WiFi.begin(wifi_ssid, wifi_password) ;
  mqtt_client.begin(mqtt_host, mqtt_port, wifi_client) ;
  mqtt_client.onMessage(callback);

  tmp = lum = hmdt = -1.0 ;

  first_time = true ;

  /*Time begins now!*/
  t0 = t = millis() ;

  /*Button*/
  pinMode(ledPin, OUTPUT);
  // initialize the pushbutton pin as an input:
  pinMode(buttonPin, INPUT);

  /*Mac Address*/
  WiFi.macAddress(mac);
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

  t = millis() ;
  if ( first_time || (t - t0) >= DELTA_T ) {
    t0 = t ;
    first_time = false ;

    lum = getLum() ;
    tmp = getTmp() ;
    hmdt = getHmdt() ;

    sendValues() ;
    if(buttonState == LOW){
      delay(10000);
    }else{
      delay(2000);
    }
  }

  /*Button */
  buttonState = digitalRead(buttonPin);

  // check if the pushbutton is pressed. If it is, the buttonState is HIGH:
  if (buttonState == HIGH) {
    // turn LED on:
    digitalWrite(ledPin, HIGH);
  } else {
    // turn LED off:
    digitalWrite(ledPin, LOW);
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
   publishes a message on the topic+"/" containing
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

  mqtt_client.subscribe("#");


  /*Mac Address*/
  WiFi.macAddress(mac);

  /* If you want to subscribe to topics, you have to do it here. */
}

/* ################################################################### */
/* This function handles the sending of all the values
   collected by the sensors.*/
void sendValues() {
  if ( mqtt_client.connected() ) {
    if ( tmp != -1 ){
      mqtt_client.publish(String(topic + "/temp").c_str(), String("{\"temp\":\"" + String(tmp) + "\", \"light\":\"" + lum + "\",\"hmdt\":\"" + hmdt + "\",\"address\":\"" + mac[5] + ":" + mac[4] + ":" + mac[3] + ":" + mac[2] + ":" + mac[1] + ":" + mac[0] + "\",\"button\":" + buttonState + "}").c_str());
    }
  }
}

/* ################################################################### */
/* The main callback to listen to topics.
*/
void callback(String &intopic, String &payload) {
  /* There's nothing to do here ... as long as the module
      cannot handle messages.*/
  Serial.println("incoming: " + intopic + " - " + payload);
}