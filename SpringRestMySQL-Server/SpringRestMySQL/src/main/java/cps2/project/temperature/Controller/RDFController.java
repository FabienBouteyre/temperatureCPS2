package cps2.project.temperature.Controller;


import cps2.project.temperature.Service.ServiceRDF;
import org.apache.jena.rdf.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;

@Controller
@RequestMapping(path = "/api/rdf")
public class RDFController {

    public Model model = ServiceRDF.GetModelRDF();

    @GetMapping(path = "/ldjson", produces = {"application/x-javascript", "application/json", "application/ld+json"})
    public @ResponseBody String GetLdJson(){

        return "rdf";
    }

    @GetMapping(path = "/turtle", produces = {"text/turtle"})
    public @ResponseBody String GetTurtle(){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        model.write(os, "TURTLE");
        return os.toString();
    }

    @GetMapping(path = "/ntriple", produces = {"application/n-triples"})
    public @ResponseBody String GetNTriples(){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        model.write(os, "N-TRIPLE");
        return os.toString();
    }

    @GetMapping(path = "/n3", produces = {"text/n3"})
    public @ResponseBody String GetN3(){
       ByteArrayOutputStream os = new ByteArrayOutputStream();
       model.write(os, "N3");
       return os.toString();
    }

}
