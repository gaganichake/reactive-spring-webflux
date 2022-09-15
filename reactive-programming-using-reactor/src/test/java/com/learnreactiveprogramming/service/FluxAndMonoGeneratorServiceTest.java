package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        //given

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFlux();

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("Foo", "Bar", "Temp")
                //.expectNextCount(3)
                .expectNext("Foo")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesMono() {
        //given

        //when
        Mono<String> namesFlux = fluxAndMonoGeneratorService.nameMono();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("Me")
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void namesFluxMap() {
        //given

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxMap();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("FOO", "BAR", "TEMP")
                .verifyComplete();
    }

    @Test
    void namesFluxImmutable() {
        //given

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxImmutable();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("Foo", "Bar", "Temp")
                .verifyComplete();
    }

    @Test
    void namesFluxFilter() {
        //given
        int nameLength = 3;

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxFilter(nameLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("TEMP")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMap() {
        //given

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMap();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("F", "O", "O", "B", "A", "R", "T", "E", "M", "P")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMapAsync() {
        //given

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMapAsync();

        //then
        StepVerifier.create(namesFlux)
                // .expectNext("F", "O", "O", "B", "A", "R", "T", "E", "M", "P") // Ordering cannot be expected with time delay
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void namesFluxConcatMap() {
        //given

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxConcatMap();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("F", "O", "O", "B", "A", "R", "T", "E", "M", "P")
                .verifyComplete();
        // Ordering is maintained even with time delay by waiting for the previous operation to complete.
    }

    @Test
    void namesMonoFlatMap() {
        //given

        //when
        Mono<List<String>> value = fluxAndMonoGeneratorService.namesMonoFlatMap();

        //then
        StepVerifier.create(value)
                .expectNext(List.of("F", "O", "O"))
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMapMany() {
        //given

        //when
        Flux<String> value = fluxAndMonoGeneratorService.namesMonoFlatMapMany();

        //then
        StepVerifier.create(value)
                .expectNext("F", "O", "O")
                .verifyComplete();
    }

    @Test
    void namesFluxTransform() {
        //given
        int nameLength = 3;

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxTransform(nameLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("TEMP")
                .verifyComplete();
    }

    @Test
    void namesFluxDefaultIfEmpty() {
        //given
        int nameLength = 5;

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxDefaultIfEmpty(nameLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("-")
                .verifyComplete();
    }

    @Test
    void namesFluxSwitchIfEmpty() {
        //given
        int nameLength = 5;

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxSwitchIfEmpty(nameLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("Zootopia", "Toofan", "Kooho")
                .verifyComplete();
    }

    @Test
    void concatFlux() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.concatFlux();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("a", "b", "c", "d", "A", "B", "C", "D")
                .verifyComplete();
    }

    @Test
    void concatWithFlux() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.concatWithFlux();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("a", "b", "c", "d", "A", "B", "C", "D")
                .verifyComplete();
    }

    @Test
    void concatWithMono() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.concatWithMono();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("a", "A", "B", "C", "D")
                .verifyComplete();
    }

    @Test
    void mergeFlux() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.mergeFlux();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("a", "A", "b", "B", "c", "d", "C", "D")
                .verifyComplete();
    }

    @Test
    void mergeWithFlux() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.mergeWithFlux();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("a", "A", "b", "B", "c", "d", "C", "D")
                .verifyComplete();
    }

    @Test
    void mergeWithMono() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.mergeWithMono();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("a", "A", "B", "C", "D")
                .verifyComplete();
    }

    @Test
    void mergeSequentialFlux() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.mergeSequentialFlux();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("a", "b", "c", "d", "A", "B", "C", "D")
                .verifyComplete();
    }

    @Test
    void zipFlux() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.zipFlux();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("aA", "bB", "cC", "dD")
                .verifyComplete();
    }

    @Test
    void zipFluxMap() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.zipFluxMap();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("aA", "bB", "cC", "dD")
                .verifyComplete();
    }

    @Test
    void zipFluxTuple4Map() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.zipFluxTuple4Map();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("aA1@", "bB2#", "cC3$", "dD4&")
                .verifyComplete();
    }

    @Test
    void zipWithFlux() {
        //given

        //when
        Flux<String> letterFlux = fluxAndMonoGeneratorService.zipWithFlux();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("aA", "bB", "cC", "dD")
                .verifyComplete();
    }

    @Test
    void zipWithMono() {
        //given

        //when
        Mono<String> letterFlux = fluxAndMonoGeneratorService.zipWithMono();

        //then
        StepVerifier.create(letterFlux)
                .expectNext("aA")
                .verifyComplete();
    }
}