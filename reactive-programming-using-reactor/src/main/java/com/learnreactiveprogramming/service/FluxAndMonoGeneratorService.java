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

        fluxAndMonoGeneratorService.namesFluxTransform()
                .subscribe(name -> System.out.println("Flux Transformed Name is " + name));

        fluxAndMonoGeneratorService.namesFluxImmutable()
                .subscribe(name -> System.out.println("Flux Immutable Name is " + name));
    }

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .log();// DB or remote service call
    }

    public Mono<String> nameMono() {
        return Mono.just("Me")
                .log();
    }

    public Flux<String> namesFluxTransform() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .map(String::toUpperCase)
//                .map(s -> toString().toUpperCase())
                .log();
    }

    public Flux<String> namesFluxImmutable() {
        Flux<String> namesFlux = Flux.fromIterable(List.of("Foo", "Bar", "Temp"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }
}
