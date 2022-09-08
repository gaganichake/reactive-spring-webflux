package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

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
    void namesFluxTransform() {
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
}