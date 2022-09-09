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
}