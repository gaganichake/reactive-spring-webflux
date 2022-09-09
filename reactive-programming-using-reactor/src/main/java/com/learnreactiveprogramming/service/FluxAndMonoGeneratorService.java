package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

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
                .subscribe(name -> System.out.println("Flux async Name char is " + name));

        fluxAndMonoGeneratorService.namesFluxConcatMap()
                .subscribe(name -> System.out.println("Flux concat Name char is " + name));

        fluxAndMonoGeneratorService.namesMonoFlatMap()
                .subscribe(name -> System.out.println("Mono flat map Name char is " + name));

        fluxAndMonoGeneratorService.namesMonoFlatMapMany()
                .subscribe(name -> System.out.println("Mono flat map many Name char is " + name));

        fluxAndMonoGeneratorService.namesFluxTransform(3)
                .subscribe(name -> System.out.println("Flux transform Name is " + name));
    }

    // Create a Flux object (1 or N) from the data source
    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .log();// DB or remote service call
    }

    // Create a Mono object (0 or 1) from the data source
    public Mono<String> nameMono() {
        return Mono.just("Me")
                .log();
    }

    // Transform data (one to one) in a Flux pipeline. Synchronous transformation.
    public Flux<String> namesFluxMap() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                //.map(s -> s.toUpperCase())
                .map(String::toUpperCase)
                .log();
    }

    // Reactive Steams are immutable
    public Flux<String> namesFluxImmutable() {
        Flux<String> namesFlux = Flux.fromIterable(List.of("Foo", "Bar", "Temp"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    // Filter element
    public Flux<String> namesFluxFilter(int nameLength) {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > nameLength);
    }

    // Advance transformation using FlatMap. Transforming data (one to many) in a Flux pipeline
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

    // Advance transformation using FlatMap. Operate Asynchronously.
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

    // Advance transformation using FlatMap. Operate Synchronously.
    public Flux<String> namesFluxConcatMap() {
        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .map(String::toUpperCase)
                .concatMap(this::splitStringWithDelay)
                .log();
    }

    // Advance transformation using FlatMap. Transforming Mono object from Mono object
    public Mono<List<String>> namesMonoFlatMap() {
        return Mono.just("foo")
                .map(String::toUpperCase)
                .flatMap(this::splitStringMono)
                .log();
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    // When a Mono pipeline needs to return a Flux, use FlatMapMany
    public Flux<String> namesMonoFlatMapMany() {
        return Mono.just("foo")
                .map(String::toUpperCase)
                .flatMapMany(this::splitString)
                .log();
    }

    // Combine multiple transform operations
    public Flux<String> namesFluxTransform(int nameLength) {

        Function<Flux<String>, Flux<String>> transform = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > nameLength);

        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .transform(transform)
                .log();
    }
}
