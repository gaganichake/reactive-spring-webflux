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

    // Return a default value if empty
    public Flux<String> namesFluxDefaultIfEmpty(int nameLength) {

        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .filter(s -> s.length() > nameLength)
                .defaultIfEmpty("-")
                .log();
    }

    // Switch to an alternative Publisher (Flux or Mono) if empty
    public Flux<String> namesFluxSwitchIfEmpty(int nameLength) {

        Flux<String> alternateSource = Flux.fromIterable(List.of("Zootopia", "Toofan", "Kooho"));

        return Flux.fromIterable(List.of("Foo", "Bar", "Temp"))
                .filter(s -> s.length() > nameLength)
                .switchIfEmpty(alternateSource)
                .log();
    }

    // concat & concatWith: Combine two reactive steams in to one
    // Concatenation of Reactive Steams happens in a sequence (Synchronously)
    // One publisher subscribes first and completes before second subscribes.
    // Second publisher subscribed after first one and completes in sequence.

    // Combine two Flux using static method "concat"
    public Flux<String> concatFlux() {

        Flux<String> lowerLetters = Flux.just("a", "b", "c", "d");

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D");

        return Flux.concat(lowerLetters, upperLetters).log(); // You can combine more than two Flux
    }

    // Combine two Flux using instance method "concatWith"
    public Flux<String> concatWithFlux() {

        Flux<String> lowerLetters = Flux.just("a", "b", "c", "d");

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D");

        return lowerLetters.concatWith(upperLetters).log(); // You can combine only two Flux
    }

    // Combine two Flux using instance method "concatWith"
    public Flux<String> concatWithMono() {

        Mono<String> lowerLetters = Mono.just("a");

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D");

        return lowerLetters.concatWith(upperLetters).log(); // You can combine only two Flux
    }

    // merger & mergeWith: Combine two reactive steams in to one.
    // Both the publishers are subscribed at the same time.
    // Publishers are subscribed eagerly and the merge happens in an interleaved fashion

    // Combine two Flux using static method "merge"
    public Flux<String> mergeFlux() {

        Flux<String> lowerLetters = Flux.just("a", "b", "c", "d")
                .delayElements(Duration.ofMillis(100));

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D")
                .delayElements(Duration.ofMillis(150));

        return Flux.merge(lowerLetters, upperLetters).log(); // You can combine more than two Flux
    }

    // Combine two Flux using instance method "mergeWith"
    public Flux<String> mergeWithFlux() {

        Flux<String> lowerLetters = Flux.just("a", "b", "c", "d")
                .delayElements(Duration.ofMillis(100));

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D")
                .delayElements(Duration.ofMillis(150));

        return lowerLetters.mergeWith(upperLetters).log(); // You can combine only two Flux
    }

    // Combine two Flux using instance method "mergeWith"
    public Flux<String> mergeWithMono() {

        Mono<String> lowerLetters = Mono.just("a")
                .delayElement(Duration.ofMillis(100));

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D")
                .delayElements(Duration.ofMillis(150));

        return lowerLetters.mergeWith(upperLetters).log(); // You can combine only two Flux
    }

    // mergeSequential: Combine two reactive steams in to one.
    // Both the publishers are subscribed at the same time.
    // Publishers are subscribed eagerly however the merge happens in a sequence

    // Combine two Flux using static method "mergeSequential"
    public Flux<String> mergeSequentialFlux() {

        Flux<String> lowerLetters = Flux.just("a", "b", "c", "d")
                .delayElements(Duration.ofMillis(100));

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D")
                .delayElements(Duration.ofMillis(150));

        return Flux.mergeSequential(lowerLetters, upperLetters).log(); // You can combine more than two Flux
    }

    // Transform two Flux using static method "zip"
    public Flux<String> zipFlux() {

        Flux<String> lowerLetters = Flux.just("a", "b", "c", "d");

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D");

        return Flux.zip(lowerLetters, upperLetters, (lower, upper) -> lower + upper)
                .log(); // You can combine up to 8 Flux
    }

    // Transform two Flux using static method "zip"
    public Flux<String> zipFluxMap() {

        Flux<String> lowerLetters = Flux.just("a", "b", "c", "d");

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D");

        return Flux.zip(lowerLetters, upperLetters)
                .map(tuple2 -> tuple2.getT1() + tuple2.getT2())
                .log(); // You can combine up to 8 Flux
    }

    // Transform four Flux using static method "zip"
    public Flux<String> zipFluxTuple4Map() {

        Flux<String> lowerLetters = Flux.just("a", "b", "c", "d");

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D");

        Flux<String> numberLetters = Flux.just("1", "2", "3", "4");

        Flux<String> specialLetters = Flux.just("@", "#", "$", "&");

        return Flux.zip(lowerLetters, upperLetters, numberLetters, specialLetters)
                .map(tuple4 -> tuple4.getT1() + tuple4.getT2() + tuple4.getT3() + tuple4.getT4())
                .log(); // You can combine up to 8 Flux
    }

    // Transform two Flux using instance method "zipWith"
    public Flux<String> zipWithFlux() {

        Flux<String> lowerLetters = Flux.just("a", "b", "c", "d");

        Flux<String> upperLetters = Flux.just("A", "B", "C", "D");

        return lowerLetters.zipWith(upperLetters, (lower, upper) -> lower + upper)
                .log();
    }

    // Transform two Mono using instance method "zipWith"
    public Mono<String> zipWithMono() {

        Mono<String> lowerLetters = Mono.just("a");

        Mono<String> upperLetters = Mono.just("A");

        return lowerLetters.zipWith(upperLetters)
                .map(tuple2 -> tuple2.getT1() + tuple2.getT2())
                .log();
    }
}
