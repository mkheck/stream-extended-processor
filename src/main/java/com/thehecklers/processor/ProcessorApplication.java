package com.thehecklers.processor;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SpringBootApplication
public class ProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessorApplication.class, args);
    }

}

@Configuration
class AircraftProcessor {
/*    @Bean
    Function<Aircraft, EssentialAircraft> transformAC() {
        return ac -> {
            EssentialAircraft essentialAircraft = new EssentialAircraft(ac.getCallsign(),
                    ac.getReg(),
                    ac.getType());

            System.out.println(essentialAircraft);
            
            return essentialAircraft;
        };
    }*/

/*
    @Bean
    Function<List<Aircraft>, List<EssentialAircraft>> transformAC() {
        return listAC -> {
            List<EssentialAircraft> listEAC = new ArrayList<>();

            listAC.forEach(ac -> listEAC.add(new EssentialAircraft(ac.getCallsign(),
                    ac.getReg(),
                    ac.getType())));

            listEAC.forEach(System.out::println);
            System.out.println("");

            return listEAC;
        };
    }
*/

    /*@Bean
    Function<Flux<Aircraft>, Flux<EssentialAircraft>> transformAC() {
        return convertIt().andThen(reverseReg());
    }*/

    @Bean
    Function<Flux<Aircraft>, Flux<EssentialAircraft>> convertIt() {
        return fluxAC -> fluxAC.map(ac -> new EssentialAircraft(ac.getCallsign(),
                ac.getReg(),
                ac.getType()));
        //.log();
    }

    @Bean
    Function<Flux<EssentialAircraft>, Flux<EssentialAircraft>> reverseReg() {
        return fluxEAC -> fluxEAC.map(eac -> new EssentialAircraft(eac.getCallsign(),
                new StringBuffer(eac.getReg()).reverse().toString(),
                eac.getType()))
            .log();
    }
}

@Data
class EssentialAircraft {
    private String callsign, reg, type, mfr;

    public EssentialAircraft(String callsign, String reg, String type) {
        this.callsign = callsign;
        this.reg = reg;
        this.type = type;

        setMfr(type);
    }

    private void setMfr(String type) {
        mfr = switch (type.substring(0, 2)) {
            case "A2", "A3" -> "Airbus";
            case "BE", "B3" -> "Beechcraft";
            case "B7" -> "Boeing";
            case "C1", "C2", "C4", "C6", "C7", "T2" -> "Cessna";
            case "CR", "E7" -> "Embraer";
            case "LJ" -> "Learjet";
            case "MD" -> "McDonnell Douglas";
            case "PA" -> "Piper";
            default -> "Other";
        };
    }
}

@Data
class Aircraft {
    private String callsign, reg, flightno, type;
    private int altitude, heading, speed;
    private double lat, lon;
}