package cps2.project.temperature.Controller;


import cps2.project.temperature.Entity.SensorID;
import cps2.project.temperature.Entity.User;
import cps2.project.temperature.Repository.RepSensorData;
import cps2.project.temperature.Repository.RepSensorID;
import cps2.project.temperature.Repository.RepUser;
import cps2.project.temperature.Service.ServiceRDF;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Seq;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/rdf")
public class RDFController {

    private List<String> formats = new ArrayList<String>(Arrays.asList("TURTLE", "TTL", "Turtle", "N-TRIPLES", "N-TRIPLE", "NT", "JSON-LD", "RDF/XML-ABBREV", "RDF/XML", "N3", "RDF/JSON"));


    final String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
    final String owl = "http://dbpedia.org/ontology/";
    final String db = "dbpedia.org/resource/";
    final String geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";
    final String latitude = "http://www.w3.org/2003/01/geo/wgs84_pos#lat";
    final String longitude = "http://www.w3.org/2003/01/geo/wgs84_pos#long";
    final String Proute = "http://localhost/route#";
    final String Ptrip = "http://localhost/trip#";
    final String Pstop = "http://localhost/stop#";
    final String xsdString = XSD.xstring.getURI();
    final String xsdTime = XSD.time.getURI();
    final String api = "localhost:8080/api/";
    final String sensor = "localhost:8080/api/sensor/";
    final String sensorData = "localhost:8080/api/sensorData/";
    final String schema = "http://schema.org/";
    final String advice = "localhost:8080/api/advice/";
    final String ssn = "http://www.w3.org/ns/ssn/";
    final String sosa = "http://www.w3.org/ns/sosa/";
    final String qudt_unit_1_1 = "http://qudt.org/1.1/vocab/unit/";


    @Autowired
    private ServiceRDF serviceRDF;

    @Autowired
    private RepSensorID repSensorID;

    @Autowired
    private RepSensorData repSensorData;

    @Autowired
    private RepUser repUser;

    @GetMapping(value = "/users", produces = {"text/turtle"})
    public String GetUser(){
        Model model = createModel();
        Seq seq = model.createSeq();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        List<User> users = repUser.findAll();

        for (User user : users){

            Resource res1 = model.createResource();
            Resource res = model.createResource()
                    .addProperty(model.createProperty(FOAF.getURI(), "Person"), res1
                        .addProperty(model.createProperty(FOAF.getURI(), "labelProperty"), model.createResource()
                                .addProperty(model.createProperty(XSD.getURI(), "ID"), model.createLiteral("" + user.getId(), XSD.xstring.getURI()))
                        )
                        .addProperty(model.createProperty(FOAF.getURI(), "labelProperty"), model.createResource()
                                .addProperty(model.createProperty(XSD.getURI(), "boolean"), model.createLiteral("" + user.isActive(), XSD.xstring.getURI()))
                        )
                        .addProperty(model.createProperty(FOAF.getURI(), "labelProperty"), model.createResource()
                                .addProperty(model.createProperty(FOAF.getURI(), "mbox"), model.createLiteral(user.getEmail(), XSD.xstring.getURI()))
                                .addProperty(model.createProperty(FOAF.getURI(), "name"), model.createLiteral(user.getName(), XSD.xstring.getURI()))
                                .addProperty(model.createProperty(FOAF.getURI(), "familyName"), model.createLiteral(user.getSurn(), XSD.xstring.getURI()))
                        )
                        .addProperty(model.createProperty(FOAF.getURI(), "labelProperty"), model.createResource()
                                .addProperty(model.createProperty(XSD.getURI(), "string"), model.createLiteral("" + user.getPass(), XSD.xstring.getURI()))
                        )
                    );

            for (int i = 0; i < user.getRoles().size(); i++) {

                res1.addProperty(model.createProperty(api, "userID/roles"), model.createResource()
                        .addProperty(model.createProperty(RDF.getURI(), "Property"), model.createResource()
                                .addProperty(model.createProperty(XSD.getURI(), "ID"), model.createLiteral("" + i, XSD.integer.getURI()))

                        )
                        .addProperty(model.createProperty(RDF.getURI(), "Property"), model.createResource()
                                .addProperty(model.createProperty(RDFS.label.getURI()), model.createLiteral(user.getRoles().toString(), XSD.xstring.getURI()))

                        )
                );
            }



            seq.add(res);
        }

        model.write(out, "TURTLE");
        return out.toString();
    }

    @GetMapping(value = "/data/current", produces = {"text/turtle"})
    public String GetDataCurrentRoom() {

        Model model = createModel();
        Seq seq = model.createSeq();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<SensorID> sensorIDS = repSensorID.findAll();
        for (int i = 0; i < sensorIDS.size(); i++) {
            SensorID sensorID = sensorIDS.get(i);
            sensorID.setSensorDataEntity(repSensorData.findTopBySensoridOrderByDateDesc(sensorID));
            sensorIDS.set(i, sensorID);


            Resource res = model.createResource()
                    .addProperty(model.createProperty(sensor, "SensorID")
                            , model.createResource()
                                    .addProperty(model.createProperty(ssn, "hasProperty"), model.createLiteral("" + sensorID.getId(), XSD.ID.getURI()))
                                    .addProperty(RDFS.label, sensorID.getRoom())
                                    .addProperty(RDFS.comment, sensorID.getDescrib())
                                    .addProperty(model.createProperty(sensorData, "SensorDataID"), model.createResource()
                                            .addProperty(model.createProperty(sosa, "resultTime"), model.createLiteral("" + sensorID.getSensorDataEntity().getDate(), XSD.dateTime.getURI()))
                                            .addProperty(model.createProperty(sensorData, "SensorDataID/humidity"), model.createResource()
                                                    .addProperty(model.createProperty(sosa, "hasResult"), model.createLiteral("" + sensorID.getSensorDataEntity().getHmdt(), XSD.xfloat.getURI()))
                                                    .addProperty(model.createProperty(schema, "unitCode"), model.createResource()
                                                            .addProperty(model.createProperty(qudt_unit_1_1, ""), model.createLiteral("Percent", XSD.xstring.getURI()))
                                                    )
                                            )
                                            .addProperty(model.createProperty(sensorData, "SensorDataID/light"), model.createResource()
                                                    .addProperty(model.createProperty(sosa, "hasResult"), model.createLiteral("" + sensorID.getSensorDataEntity().getLight(), XSD.xfloat.getURI()))
                                                    .addProperty(model.createProperty(schema, "unitCode"), model.createResource()
                                                            .addProperty(model.createProperty(qudt_unit_1_1, ""), model.createLiteral("Percent", XSD.xstring.getURI()))
                                                    )
                                            )
                                            .addProperty(model.createProperty(sensorData, "SensorDataID/temperature"), model.createResource()
                                                    .addProperty(model.createProperty(sosa, "hasResult"), model.createLiteral("" + sensorID.getSensorDataEntity().getValue(), XSD.xfloat.getURI()))
                                                    .addProperty(model.createProperty(schema, "unitCode"), model.createResource()
                                                            .addProperty(model.createProperty(qudt_unit_1_1, ""), model.createLiteral("Percent", XSD.xstring.getURI()))
                                                    )
                                            )
                                            .addProperty(model.createProperty(ssn, "isPropertyOf"), model.createResource()
                                                    .addProperty(model.createProperty(XSD.getURI(), "integer"), model.createLiteral("" + sensorID.getId(), XSD.integer.getURI()))
                                            )
                                    )
                    );
            seq.add(res);
        }
        model.write(out, "TURTLE");

        return out.toString();
    }





    @GetMapping(path = "/ldjson", produces = {"application/x-javascript", "application/json", "application/ld+json"})
    public String GetLdJson(){

        return "rdf";
    }

    @GetMapping(path = "/turtle", produces = {"text/turtle"})
    public String GetTurtle(){
        Model model = serviceRDF.GetModelRDF();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        model.write(os, "TURTLE");
        return os.toString();
    }

    @GetMapping(path = "/ntriple", produces = {"application/n-triples"})
    public String GetNTriples(){
        Model model = serviceRDF.GetModelRDF();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        model.write(os, "N-TRIPLE");
        return os.toString();
    }

    @GetMapping(path = "/n3", produces = {"text/n3"})
    public String GetN3(){
        Model model = serviceRDF.GetModelRDF();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        model.write(os, "N3");
        return os.toString();
    }

    private Model createModel(){
        Model model = ModelFactory.createDefaultModel();
        model.setNsPrefix("rdfs",RDFS.getURI());
        model.setNsPrefix("rdf", RDF.getURI());
        model.setNsPrefix("owl", OWL.getURI());
        model.setNsPrefix("db", db);
        model.setNsPrefix("xsdString", xsdString);
        model.setNsPrefix("xsdTime", xsdTime);
        model.setNsPrefix("geo", geo);
        model.setNsPrefix("latitude", latitude);
        model.setNsPrefix("longitude", longitude);
        model.setNsPrefix("Proute", Proute);
        model.setNsPrefix("Ptrip", Ptrip);
        model.setNsPrefix("Pstop", Pstop);
        model.setNsPrefix("api", api);
        model.setNsPrefix("sensor", sensor);
        model.setNsPrefix("sensorData", sensorData);
        model.setNsPrefix("advice", advice);
        model.setNsPrefix("ssn", ssn);
        model.setNsPrefix("sosa", sosa);
        model.setNsPrefix("schema", schema);
        model.setNsPrefix("qudt-unit-1-1", qudt_unit_1_1);
        return model;
    }
}
