/// Vista "Dispositivos" — gestión de dispositivos conocidos
use egui::{Frame, Margin, RichText, Ui, Vec2};

use crate::ui::{theme::*, widgets::*};

#[derive(Debug, Clone)]
pub struct KnownDevice {
    pub name: String,
    pub connection_type: String,
    pub last_seen: String,
    pub is_active: bool,
}

pub struct DevicesState {
    pub devices: Vec<KnownDevice>,
}

impl Default for DevicesState {
    fn default() -> Self {
        Self {
            devices: vec![
                KnownDevice {
                    name: "Samsung Galaxy S24 Ultra".into(),
                    connection_type: "USB Directo".into(),
                    last_seen: "Ahora".into(),
                    is_active: true,
                },
                KnownDevice {
                    name: "Pixel 8 Pro".into(),
                    connection_type: "Wi-Fi".into(),
                    last_seen: "Hace 2 días".into(),
                    is_active: false,
                },
                KnownDevice {
                    name: "iPhone 15 Pro".into(),
                    connection_type: "USB-C".into(),
                    last_seen: "Hace 1 semana".into(),
                    is_active: false,
                },
            ],
        }
    }
}

pub fn render(ui: &mut Ui, state: &mut DevicesState) {
    ui.heading(RichText::new("Dispositivos").color(TEXT_PRIMARY).size(18.0));
    ui.label(RichText::new("Dispositivos conocidos y configuración de emparejamiento").color(TEXT_MUTED).size(12.0));
    ui.add_space(16.0);

    for device in &state.devices {
        card(ui, |ui| {
            ui.horizontal(|ui| {
                Frame::new()
                    .fill(BG_SURFACE)
                    .corner_radius(8.0)
                    .inner_margin(Margin::same(8))
                    .show(ui, |ui| {
                        ui.label(RichText::new("📱").size(20.0));
                    });

                ui.add_space(10.0);

                ui.vertical(|ui| {
                    ui.horizontal(|ui| {
                        ui.label(
                            RichText::new(&device.name)
                                .color(TEXT_PRIMARY)
                                .size(14.0)
                                .strong(),
                        );
                        if device.is_active {
                            ui.add_space(6.0);
                            Frame::new()
                                .fill(GREEN_STABLE.gamma_multiply(0.15))
                                .corner_radius(10.0)
                                .inner_margin(Margin::symmetric(8, 3))
                                .stroke(egui::Stroke::new(1.0, GREEN_STABLE))
                                .show(ui, |ui| {
                                    ui.horizontal(|ui| {
                                        let (r, _) = ui.allocate_exact_size(Vec2::splat(6.0), egui::Sense::hover());
                                        ui.painter().circle_filled(r.center(), 3.0, GREEN_DOT);
                                        ui.label(RichText::new("Activo").color(GREEN_STABLE).size(10.0));
                                    });
                                });
                        }
                    });
                    ui.label(
                        RichText::new(format!("{}  ·  Última conexión: {}", device.connection_type, device.last_seen))
                            .color(TEXT_SECONDARY)
                            .size(11.5),
                    );
                });

                ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
                    if ui.button(RichText::new("🗑").color(TEXT_MUTED).size(14.0)).clicked() {}
                    ui.add_space(6.0);
                    if ui.button(RichText::new("⚙").color(TEXT_SECONDARY).size(14.0)).clicked() {}
                    ui.add_space(6.0);
                    if !device.is_active {
                        let conn_btn = egui::Button::new(
                            RichText::new("Conectar").color(ACCENT_CYAN).size(12.0),
                        )
                        .stroke(egui::Stroke::new(1.0, ACCENT_CYAN))
                        .fill(egui::Color32::TRANSPARENT)
                        .corner_radius(6.0);
                        if ui.add(conn_btn).clicked() {}
                    }
                });
            });
        });
        ui.add_space(8.0);
    }

    ui.add_space(8.0);

    // Botón emparejar nuevo
    let pair_btn = egui::Button::new(
        RichText::new("＋  Emparejar nuevo dispositivo").color(ACCENT_CYAN).size(13.0),
    )
    .fill(BG_SURFACE)
    .stroke(egui::Stroke::new(1.0, ACCENT_CYAN))
    .corner_radius(8.0)
    .min_size(Vec2::new(ui.available_width(), 40.0));

    if ui.add(pair_btn).clicked() {}
}
