package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoGeneratorService {

    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> System.out.println("Flux Name is " + name));

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name -> System.out.println("Mono Name is " + name));
    }

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"));
    }

    public Mono<String> nameMono() {
        return Mono.just("Me");
    }
}
