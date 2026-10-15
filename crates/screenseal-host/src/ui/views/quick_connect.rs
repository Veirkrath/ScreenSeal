/// Vista "Conexión Rápida" — pantalla principal del mockup
use egui::{Color32, Frame, Margin, RichText, Ui, Vec2};

use crate::ui::{
    state::{AppState, PhonePosition, ProjectionMode},
    theme::*,
    widgets::*,
};

pub fn render(ui: &mut Ui, state: &mut AppState) {
    // ── Panel de dispositivo ──────────────────────────────────────────────────
    device_header(ui, state);

    ui.add_space(12.0);

    // ── Dos columnas: acción izquierda | preview derecha ─────────────────────
    let available = ui.available_size();
    ui.columns(2, |cols| {
        // Columna izquierda — acción principal
        left_panel(&mut cols[0], state, available.x / 2.0);
        // Columna derecha — vista previa móvil
        right_panel(&mut cols[1], state);
    });

    ui.add_space(12.0);

    // ── Controles inferiores ──────────────────────────────────────────────────
    bottom_controls(ui, state);
}

// ─── Header del dispositivo ───────────────────────────────────────────────────

fn device_header(ui: &mut Ui, state: &AppState) {
    card(ui, |ui| {
        ui.horizontal(|ui| {
            // Ícono de teléfono (placeholder)
            Frame::new()
                .fill(BG_SURFACE)
                .corner_radius(8.0)
                .inner_margin(Margin::same(6))
                .show(ui, |ui| {
                    ui.label(RichText::new("📱").size(22.0));
                });

            ui.add_space(10.0);

            // Nombre del dispositivo
            ui.vertical(|ui| {
                ui.horizontal(|ui| {
                    ui.label(
                        RichText::new(&state.device.name)
                            .color(TEXT_PRIMARY)
                            .size(15.0)
                            .strong(),
                    );
                    ui.add_space(4.0);
                    Frame::new()
                        .fill(BG_SURFACE)
                        .corner_radius(4.0)
                        .inner_margin(Margin::symmetric(6, 2))
                        .show(ui, |ui| {
                            ui.label(
                                RichText::new(&state.device.model_tag)
                                    .color(ACCENT_CYAN)
                                    .size(10.0),
                            );
                        });
                });
                ui.horizontal(|ui| {
                    // Dot verde
                    let (dot_rect, _) =
                        ui.allocate_exact_size(Vec2::splat(8.0), egui::Sense::hover());
                    ui.painter().circle_filled(dot_rect.center(), 3.5, GREEN_DOT);
                    ui.label(
                        RichText::new(&state.device.connection)
                            .color(GREEN_STABLE)
                            .size(11.5),
                    );
                });
            });

            ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
                // Ajustes rápidos
                if ui
                    .button(RichText::new("⚙ Ajustes rápidos").color(TEXT_SECONDARY).size(12.0))
                    .clicked()
                {}
                ui.add_space(12.0);
                // Latencia
                ui.label(
                    RichText::new(format!("⚡ {} ms latencia", state.device.latency_ms))
                        .color(ACCENT_CYAN)
                        .size(12.0),
                );
            });
        });
    });
}

// ─── Panel izquierdo ─────────────────────────────────────────────────────────

fn left_panel(ui: &mut Ui, state: &mut AppState, _width: f32) {
    card(ui, |ui| {
        // Título "Listo para emitir"
        ui.label(RichText::new("LISTO PARA EMITIR").color(TEXT_MUTED).size(10.0));
        ui.add_space(4.0);
        ui.label(
            RichText::new("Todo listo para proyectar")
                .color(TEXT_PRIMARY)
                .size(20.0)
                .strong(),
        );
        ui.add_space(8.0);
        ui.label(
            RichText::new(
                "Tu teléfono ahora funciona como monitor secundario táctil de respuesta \
                 instantánea. Solo pulsa el botón para comenzar a usarlo en tu escritorio.",
            )
            .color(TEXT_SECONDARY)
            .size(12.5),
        );

        ui.add_space(16.0);

        // Botón principal
        if primary_button(ui, "▶  Iniciar pantalla extendida") {
            // stub
        }

        ui.add_space(8.0);

        // Botones secundarios
        ui.columns(2, |cols| {
            let mode_ext = state.projection_mode == ProjectionMode::Extended;
            if outline_button(&mut cols[0], "⤢  Modo Extender", if mode_ext { ACCENT_CYAN } else { TEXT_SECONDARY }) {
                state.projection_mode = ProjectionMode::Extended;
            }
            let mode_mir = state.projection_mode == ProjectionMode::Mirror;
            if outline_button(&mut cols[1], "⧉  Modo Espejo", if mode_mir { ACCENT_CYAN } else { TEXT_SECONDARY }) {
                state.projection_mode = ProjectionMode::Mirror;
            }
        });

        ui.add_space(16.0);
        subtle_separator(ui);
        ui.add_space(8.0);

        // Pie: multitouch + cifrado
        ui.horizontal(|ui| {
            ui.label(
                RichText::new("✦ Control multitáctil habilitado en teléfono")
                    .color(TEXT_MUTED)
                    .size(11.0),
            );
            ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
                ui.label(
                    RichText::new("Cifrado de pantalla local")
                        .color(TEXT_MUTED)
                        .size(11.0),
                );
            });
        });
    });
}

// ─── Panel derecho — vista previa ─────────────────────────────────────────────

fn right_panel(ui: &mut Ui, state: &AppState) {
    card(ui, |ui| {
        // Encabezado
        ui.horizontal(|ui| {
            ui.label(
                RichText::new("Vista de pantalla móvil")
                    .color(TEXT_PRIMARY)
                    .size(13.0)
                    .strong(),
            );
            ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
                Frame::new()
                    .fill(BG_SURFACE)
                    .corner_radius(4.0)
                    .inner_margin(Margin::symmetric(6, 2))
                    .show(ui, |ui| {
                        ui.label(
                            RichText::new(&state.resolution)
                                .color(ACCENT_CYAN)
                                .size(11.0),
                        );
                    });
            });
        });

        ui.add_space(8.0);

        // Área de preview (placeholder)
        Frame::new()
            .fill(BG_SURFACE)
            .corner_radius(10.0)
            .stroke(egui::Stroke::new(1.0, BORDER_SUBTLE))
            .show(ui, |ui| {
                ui.set_min_size(Vec2::new(ui.available_width(), 160.0));
                ui.vertical_centered(|ui| {
                    ui.add_space(40.0);
                    ui.label(RichText::new("🖥").size(32.0));
                    ui.add_space(8.0);
                    ui.label(
                        RichText::new("Pantalla en espera de señal")
                            .color(TEXT_SECONDARY)
                            .size(13.0)
                            .strong(),
                    );
                    ui.label(
                        RichText::new("Listo para recibir ventanas de Windows")
                            .color(TEXT_MUTED)
                            .size(11.0),
                    );
                    ui.add_space(30.0);
                });
            });

        ui.add_space(8.0);

        // Footer del preview
        ui.horizontal(|ui| {
            ui.label(RichText::new("Display #2 (Virtual)").color(TEXT_MUTED).size(10.5));
            ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
                ui.label(RichText::new("ScreenSeal Engine 3K").color(TEXT_MUTED).size(10.5));
            });
        });

        ui.add_space(10.0);
        subtle_separator(ui);
        ui.add_space(8.0);

        // Stats: Hz, resolución, táctil
        ui.columns(3, |cols| {
            stat_item(&mut cols[0], &format!("{} Hz", state.fps), "Fluidez suave", GREEN_STABLE);
            stat_item(&mut cols[1], "1080p", "Calidad nítida", GREEN_STABLE);
            stat_item(&mut cols[2], "Táctil", "Gestos activos", ACCENT_CYAN);
        });

        ui.add_space(10.0);
        subtle_separator(ui);
        ui.add_space(6.0);

        // Tip
        ui.horizontal_wrapped(|ui| {
            ui.label(RichText::new("📌").size(11.0));
            ui.label(
                RichText::new(
                    "Puedes arrastrar cualquier programa hacia el borde de tu monitor \
                     principal para moverlo al teléfono.",
                )
                .color(TEXT_MUTED)
                .size(11.0),
            );
        });
    });
}

// ─── Controles inferiores ─────────────────────────────────────────────────────

fn bottom_controls(ui: &mut Ui, state: &mut AppState) {
    card(ui, |ui| {
        ui.horizontal(|ui| {
            // Posición del teléfono
            ui.label(
                RichText::new("Ubicación del teléfono:")
                    .color(TEXT_SECONDARY)
                    .size(13.0),
            );
            ui.add_space(8.0);

            if position_toggle(ui, "A la izquierda", state.phone_position == PhonePosition::Left) {
                state.phone_position = PhonePosition::Left;
            }
            if position_toggle(ui, "A la derecha", state.phone_position == PhonePosition::Right) {
                state.phone_position = PhonePosition::Right;
            }
            if position_toggle(ui, "Arriba", state.phone_position == PhonePosition::Top) {
                state.phone_position = PhonePosition::Top;
            }

            ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
                // Botón desconectar
                let disc_btn = egui::Button::new(
                    RichText::new("⏻ Desconectar")
                        .color(Color32::WHITE)
                        .size(12.5),
                )
                .fill(Color32::from_rgb(185, 28, 28))
                .corner_radius(6.0);
                if ui.add(disc_btn).clicked() {
                    // stub
                }

                ui.add_space(12.0);
                ui.checkbox(
                    &mut state.minimize_to_tray,
                    RichText::new("Minimizar a la bandeja al emitir").color(TEXT_SECONDARY).size(12.0),
                );
                ui.add_space(12.0);
                ui.checkbox(
                    &mut state.send_audio,
                    RichText::new("Enviar audio al teléfono").color(TEXT_SECONDARY).size(12.0),
                );
            });
        });
    });
}
