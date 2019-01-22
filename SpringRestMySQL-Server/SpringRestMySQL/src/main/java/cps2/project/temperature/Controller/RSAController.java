package cps2.project.temperature.Controller;


import cps2.project.temperature.Encryption.RSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.security.krb5.internal.crypto.RsaMd5CksumType;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;

@Controller
@RequestMapping(path = "/rsa")
public class RSAController {


    @GetMapping
    public String getGenRSA(Model model){
        RSA rsa = new RSA();
        model.addAttribute("e", rsa.getPublicKey());
        model.addAttribute("d", rsa.getPrivateKey());
        model.addAttribute("N", rsa.getMode());
        System.out.println(String.format("Your e = %d \n d = %d \n N = %d", rsa.getPublicKey(), rsa.getPrivateKey(), rsa.getMode()));
        return "rsagen";
    }

    @GetMapping("/encrypt")
    public String getEnc(Model model){
        return "rsa";
    }

    @PostMapping("/encrypt")
    public String postEnc(@RequestParam HashMap<String, String> map, Model model){

        if (StringUtils.isEmpty(map.get("e")) && StringUtils.isEmpty(map.get("N"))){
            model.addAttribute("mess", "N or e is empty");
            return "rsa";
        }
        if (StringUtils.isEmpty(map.get("text"))){
            model.addAttribute("mess", "Text is empty");
            return "rsa";
        }

        BigInteger e, N;
        e = new BigInteger(map.get("e"));
        N = new BigInteger(map.get("N"));
        String str = "" + RSA.bytesToString(RSA.encryptCustom(map.get("text").getBytes(), e, N));
        model.addAttribute("mess", str);
        System.out.println(String.format("e = %d;\n N = %d;\n Enc... message on bytes = %s", e, N, str));

        model.addAttribute("e", map.get("e"));
        model.addAttribute("N", map.get("N"));
        if (StringUtils.isEmpty(map.get("e")))
            model.addAttribute("d", map.get("d"));

        return "rsa";
    }


    @GetMapping("/dencrypt")
    public String getDecr(Model model){
        return "rsa";
    }

    @PostMapping("/dencrypt")
    public String postDecr(@RequestParam HashMap<String, String> map, Model model) throws UnsupportedEncodingException {

        if (StringUtils.isEmpty(map.get("d")) && StringUtils.isEmpty(map.get("N"))){
            model.addAttribute("mess", "N or d is empty");
            return "rsa";
        }
        if (StringUtils.isEmpty(map.get("text"))){
            model.addAttribute("mess", "Text is empty");
            return "rsa";
        }

        BigInteger d, N;
        d = new BigInteger(map.get("d"));
        N = new BigInteger(map.get("N"));
        String str = "" +  new String(RSA.decryptCustom(map.get("text").getBytes(), d, N), "UTF-8");
        model.addAttribute("mess", str);
        System.out.println(String.format("d = %d;\n N = %d;\n Dec... message dtring = %s", d, N, str));

        model.addAttribute("d", map.get("d"));
        model.addAttribute("N", map.get("N"));
        if (StringUtils.isEmpty(map.get("e")))
            model.addAttribute("e", map.get("e"));

        return "rsa";
    }


}
