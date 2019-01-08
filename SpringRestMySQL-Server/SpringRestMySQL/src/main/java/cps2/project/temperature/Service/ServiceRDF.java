package cps2.project.temperature.Service;

import cps2.project.temperature.Entity.SensorData;
import cps2.project.temperature.Repository.RepSensorData;
import cps2.project.temperature.Repository.RepSensorID;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServiceRDF {

    public static final String url = "http://w3id.org/people/az/me";
    public static final String nameSpace = "http://w3id.org/people/az/me";
    public static final String namePrefix = "az";
    public static final String nameRDFS = "rdfs";
    public static final String name = "STAMKULOV Syrym";
    public static final String dboNS = "http://dbpedia.org/ontology/";
    public static final String emseurl = "http://dbpedia.org/ontology/";
    public static final String drm = "http://vocab.data.gov/def/def/drm#";
    public static final String geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";

    public static final String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String foaf = "http://xmlns.com/foaf/0.1/";
    public static final String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
    public static final String xsd = "http://www.w3.org/2001/XMLSchema";
    public static final String api = "localhost:8080/api/";
    public static final String sensor = "localhost:8080/api/sensor/";
    public static final String sensorData = "localhost:8080/api/sensorData/";
    public static final String advice = "localhost:8080/api/advice/";


    @Autowired
    private RepSensorData repSensorData;

    @Autowired
    private RepSensorID repSensorID;


    public Model GetModelRDF(){

        Model model = ModelFactory.createDefaultModel();
        model.setNsPrefix(namePrefix, nameSpace);
        model.setNsPrefix(nameRDFS, RDFS.uri);
        model.setNsPrefix("xsd", XSD.getURI());
        model.setNsPrefix("dbo", dboNS);
        model.setNsPrefix("geo", geo);
        model.setNsPrefix("rdf", rdf);
        model.setNsPrefix("foaf", foaf);
        model.setNsPrefix("rdfs", rdfs);
        model.setNsPrefix("xsd", xsd);
        model.setNsPrefix("api", api);
        model.setNsPrefix("sensor", sensor);
        model.setNsPrefix("sensorData", sensorData);
        model.setNsPrefix("advice", advice);

        Resource res1 = model.createProperty(sensor, "sensorID");
            res1.addProperty(model.createProperty(xsd, "ID"), model.createTypedLiteral("Id of the sensor", XSD.integer.getURI()));
            res1.addProperty(RDFS.label, "Room Label");
            res1.addProperty(RDFS.comment, "Description of the sensor");

        Resource resSensorData = model.createProperty(sensorData, "sensorDataID");
            resSensorData.addProperty(model.createProperty(xsd, "ID"), model.createTypedLiteral("Id of the sensorData", XSD.ID.getURI()));
            resSensorData.addProperty(model.createProperty(xsd, "dateTime"), model.createTypedLiteral("Id of the sensorData", XSD.dateTime.getURI()));
            resSensorData.addProperty(model.createProperty(sensorData, "sensorDataID"), model.createTypedLiteral("humidity"));

            Resource resSubSensorData = model.createProperty(sensorData, "SensorData");
                resSubSensorData.addProperty(RDFS.subClassOf, resSensorData.getURI());
                resSubSensorData.addProperty(model.createProperty(xsd, "float"), model.createTypedLiteral("15.0", XSD.xfloat.getURI()));
                resSubSensorData.addProperty(model.createProperty(xsd, "string"), model.createTypedLiteral("Unit", XSD.xstring.getURI()));

        Resource res = model.createProperty(url + "/Bob");
        Property dboDate3 = model.createProperty(geo, "lat");
        Property dboDate4 = model.createProperty(geo, "long");

        res.addProperty(RDFS.label, "Coordinate");
        res.addProperty(dboDate3, model.createTypedLiteral("" + repSensorData.findSensorDataById(Long.parseLong("14")).getHmdt(), XSD.decimal.getURI()));
        res.addProperty(dboDate4, model.createTypedLiteral("5151511515151", XSD.decimal.getURI()));

        return model;
    }



}
