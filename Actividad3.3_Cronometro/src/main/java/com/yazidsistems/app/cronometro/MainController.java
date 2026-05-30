package com.yazidsistems.app.cronometro;
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class MainController {

    private static final String TIEMPO_CERO = "00:00.00";

    @FXML private Label lblTiempo;
    @FXML private ListView<String> listaVueltas;
    @FXML private Button btnIniciarPausar;
    @FXML private Button btnVuelta;
    @FXML private Button btnReiniciar;

    private LongProperty tiempoTranscurrido;
    private LongProperty tiempoPausa;
    private AnimationTimer temporizador;
    private int contadorVueltas;

    @FXML
    public void initialize() {
        tiempoTranscurrido = new SimpleLongProperty(0);
        tiempoPausa = new SimpleLongProperty(0);
        contadorVueltas = 1;

        temporizador = new AnimationTimer() {
            private long inicioTiempo;

            @Override
            public void start() {
                inicioTiempo = System.nanoTime() - tiempoPausa.get();
                super.start();
            }

            @Override
            public void handle(long now) {
                tiempoTranscurrido.set(now - inicioTiempo);
                lblTiempo.setText(formatearTiempo(tiempoTranscurrido.get()));
            }
        };
    }

    @FXML
    private void manejarIniciarPausar() {
        if (btnIniciarPausar.getText().equals("Iniciar")) {
            temporizador.start();
            btnIniciarPausar.setText("Pausar");
            btnVuelta.setDisable(false);
            btnReiniciar.setDisable(false);
        } else {
            tiempoPausa.set(tiempoTranscurrido.get());
            temporizador.stop();
            btnIniciarPausar.setText("Iniciar");
        }
    }

    @FXML
    private void manejarVuelta() {
        String tiempoActual = formatearTiempo(tiempoTranscurrido.get());
        String textoVuelta = "Vuelta " + contadorVueltas + " - " + tiempoActual;
        listaVueltas.getItems().add(0, textoVuelta);
        contadorVueltas++;
    }

    @FXML
    private void manejarReiniciar() {
        temporizador.stop();
        tiempoTranscurrido.set(0);
        tiempoPausa.set(0);
        contadorVueltas = 1;
        lblTiempo.setText(TIEMPO_CERO);
        listaVueltas.getItems().clear();
        btnIniciarPausar.setText("Iniciar");
        btnVuelta.setDisable(true);
        btnReiniciar.setDisable(true);
    }

    private String formatearTiempo(long nanos) {
        long centesimas = (nanos / 1_000_000) / 10;
        long segundos = (nanos / 1_000_000_000) % 60;
        long minutos = (nanos / 1_000_000_000) / 60;
        return String.format("%02d:%02d.%02d", minutos, segundos, centesimas % 100);
    }
}
