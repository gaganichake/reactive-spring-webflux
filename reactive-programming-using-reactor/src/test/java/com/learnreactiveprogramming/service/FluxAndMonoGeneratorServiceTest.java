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
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxTransform();

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
    void namesFluxTransformFlatMap() {
        //given

        //when
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxTransformFlatMap();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("F", "O", "O", "B", "A", "R", "T", "E", "M", "P")
                .verifyComplete();
    }
}