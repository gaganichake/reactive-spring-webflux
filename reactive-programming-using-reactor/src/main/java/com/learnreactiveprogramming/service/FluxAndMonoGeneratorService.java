package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class FluxAndMonoGeneratorService {

    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> System.out.println("Flux Name is " + name));

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name -> System.out.println("Mono Name is " + name));

        fluxAndMonoGeneratorService.namesFluxMap()
                .subscribe(name -> System.out.println("Flux map Name is " + name));

        fluxAndMonoGeneratorService.namesFluxImmutable()
                .subscribe(name -> System.out.println("Flux immutable Name is " + name));

        fluxAndMonoGeneratorService.namesFluxFilter(3)
                .subscribe(name -> System.out.println("Flux filtered Name is " + name));

        fluxAndMonoGeneratorService.namesFluxFlatMap()
                .subscribe(name -> System.out.println("Flux flat map Name char is " + name));

        fluxAndMonoGeneratorService.namesFluxFlatMapAsync()
                .subscribe(name -> System.out.println("Flux Async Name char is " + name));
    }

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .log();// DB or remote service call
    }

    public Mono<String> nameMono() {
        return Mono.just("Me")
                .log();
    }

    public Flux<String> namesFluxMap() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                //.map(s -> s.toUpperCase())
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFluxImmutable() {
        Flux<String> namesFlux = Flux.fromIterable(List.of("Foo", "Bar", "Temp"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Flux<String> namesFluxFilter(int nameLength) {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > nameLength);
    }

    public Flux<String> namesFluxFlatMap() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .map(String::toUpperCase)
                //.flatMap(s -> splitString(s))
                .flatMap(this::splitString);
    }

    private Flux<String> splitString(String name) {
        String[] split = name.split("");
        return Flux.fromArray(split);
    }

    public Flux<String> namesFluxFlatMapAsync() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .map(String::toUpperCase)
                .flatMap(this::splitStringWithDelay)
                .log();
    }

    private Flux<String> splitStringWithDelay(String name) {
        String[] split = name.split("");
        long delay = 1000;
        return Flux.fromArray(split)
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> namesFluxConcatMap() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .map(String::toUpperCase)
                .concatMap(this::splitStringWithDelay)
                .log();
    }
}
