package cps2.project.temperature.Service;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;

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


    public static Model GetModelRDF(){

        Model model = ModelFactory.createDefaultModel();
        model.setNsPrefix(namePrefix, nameSpace);
        model.setNsPrefix(nameRDFS, RDFS.uri);
        model.setNsPrefix("xsd", XSD.getURI());
        model.setNsPrefix("dbo", dboNS);
        model.setNsPrefix("geo", geo);

        Resource res = model.createProperty(url + "/Bob");
        Property dboDate3 = model.createProperty(geo, "lat");
        Property dboDate4 = model.createProperty(geo, "long");

        res.addProperty(RDFS.label, "Coordinate");
        res.addProperty(dboDate3, model.createTypedLiteral("1515151515151", XSD.decimal.getURI()));
        res.addProperty(dboDate4, model.createTypedLiteral("5151511515151", XSD.decimal.getURI()));

        return model;
    }



}
