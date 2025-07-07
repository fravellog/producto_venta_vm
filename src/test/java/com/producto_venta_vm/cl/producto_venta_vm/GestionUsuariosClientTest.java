package com.producto_venta_vm.cl.producto_venta_vm;
import com.producto_venta_vm.cl.producto_venta_vm.service.GestionUsuariosClient;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

public class GestionUsuariosClientTest {

    private MockWebServer mockWebServer;
    private GestionUsuariosClient gestionUsuariosClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        gestionUsuariosClient = new GestionUsuariosClient(webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGetUsuarioPorId() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"id\":1,\"nombre\":\"Juan\"}")
                .addHeader("Content-Type", "application/json"));

        StepVerifier.create(gestionUsuariosClient.getUsuarioPorId(1L))
                .expectNext("{\"id\":1,\"nombre\":\"Juan\"}")
                .verifyComplete();
    }

    @Test
    void testGetAllUsuarios() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("[{\"id\":1,\"nombre\":\"Juan\"}]")
                .addHeader("Content-Type", "application/json"));

        StepVerifier.create(gestionUsuariosClient.getAllUsuarios())
                .expectNext("[{\"id\":1,\"nombre\":\"Juan\"}]")
                .verifyComplete();
    }
}
