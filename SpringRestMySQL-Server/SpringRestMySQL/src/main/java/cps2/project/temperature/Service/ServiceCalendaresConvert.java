package cps2.project.temperature.Service;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class ServiceCalendaresConvert {



    public void IntoCalendare(File file){






    }





//    public void ToObject() throws IOException, ParserException {
//
//        FileInputStream fileInputStream = new FileInputStream("C:/Users/Good/Desktop/Calendare/ADECal.ics");
//        CalendarBuilder calendarBuilder = new CalendarBuilder();
//        Calendar calendar = calendarBuilder.build(fileInputStream);
//
//        ComponentList cs = calendar.getComponents();
//        System.out.println(calendar.getVersion());
//        String calen = calendar.toString();
//        String[] split = calen.split("BEGIN:VEVENT");
//
//        for (int i = 0; i < split.length; i++) {
//
//            String item = split[i];
//            String DTSTAMP = item.split("DTSTAMP:")[0];
//            String DTSTART = item.split("DTSTART:")[0];
//            String DTEND = item.split("DTEND:")[0];
//            String SUMMARY = item.split("SUMMARY:")[0];
//            String LOCATION = item.split("LOCATION:")[0];
//            String DESCRIPTION = item.split("DESCRIPTION:")[0];
//            String UID = item.split("UID:")[0];
//            String CREATED = item.split("CREATED:")[0];
//            String LASTMODIFIED = item.split("LAST-MODIFIED:")[0];
//            String SEQUENCE = item.split("LAST-MODIFIED:")[0];
//
//            System.out.println(
//                    DTSTAMP + '\n'+
//                    DTSTART + '\n'+
//                    DTEND + '\n'+
//                    SUMMARY + '\n'+
//                    LOCATION + '\n'+
//                    DESCRIPTION + '\n'+
//                    UID + '\n'+
//                    CREATED + '\n'+
//                    LASTMODIFIED + '\n'+
//                    SEQUENCE + '\n'
//            );
//            System.out.println("\n\n");
//        }
//    }


}


//        BEGIN:VEVENT
//        DTSTAMP:20181201T153343Z
//        DTSTART:20180925T063000Z
//        DTEND:20180925T094500Z
//        SUMMARY:CM/TD Security\, Trust and Privacy
//        LOCATION:EMSE S224
//        DESCRIPTION:\n\nM2 CPS2 (8)\n(Exported :01/12/2018 16:33)\n
//        UID:ADE60323031382d3230313953542d455449454e4e452d31353132312d312d30
//        CREATED:19700101T000000Z
//        LAST-MODIFIED:20181201T153343Z
//        SEQUENCE:1833559623
//        END:VEVENT