package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;

import java.util.List;

public class FluxAndMonoGeneratorService {

    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> System.out.println("Name is " + name));
    }

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"));
    }
}
