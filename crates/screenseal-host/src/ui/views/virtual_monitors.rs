/// Vista "Monitores Virtuales"
use egui::{Frame, Margin, RichText, Ui, Vec2};

use crate::ui::{theme::*, widgets::*};

#[derive(Debug, Clone)]
pub struct VirtualMonitor {
    pub id: u8,
    pub label: String,
    pub resolution: String,
    pub hz: u32,
    pub active: bool,
}

pub struct VirtualMonitorsState {
    pub monitors: Vec<VirtualMonitor>,
}

impl Default for VirtualMonitorsState {
    fn default() -> Self {
        Self {
            monitors: vec![
                VirtualMonitor { id: 1, label: "Monitor Principal".into(), resolution: "2560 × 1440".into(), hz: 144, active: true },
                VirtualMonitor { id: 2, label: "Display #2 (Virtual)".into(), resolution: "1920 × 1080".into(), hz: 60, active: true },
                VirtualMonitor { id: 3, label: "Display #3 (Virtual)".into(), resolution: "1920 × 1080".into(), hz: 60, active: false },
            ],
        }
    }
}

pub fn render(ui: &mut Ui, state: &mut VirtualMonitorsState) {
    ui.heading(RichText::new("Monitores Virtuales").color(TEXT_PRIMARY).size(18.0));
    ui.label(RichText::new("Gestiona las pantallas extendidas y sus propiedades").color(TEXT_MUTED).size(12.0));
    ui.add_space(16.0);

    for mon in &mut state.monitors {
        card(ui, |ui| {
            ui.horizontal(|ui| {
                // Ícono monitor
                Frame::new()
                    .fill(BG_SURFACE)
                    .corner_radius(8.0)
                    .inner_margin(Margin::same(8))
                    .show(ui, |ui| {
                        ui.label(RichText::new("🖥").size(22.0));
                    });

                ui.add_space(10.0);

                ui.vertical(|ui| {
                    ui.horizontal(|ui| {
                        ui.label(RichText::new(format!("Display #{}", mon.id)).color(TEXT_MUTED).size(10.0));
                        ui.add_space(4.0);
                        if mon.active {
                            Frame::new()
                                .fill(GREEN_STABLE.gamma_multiply(0.2))
                                .corner_radius(4.0)
                                .inner_margin(Margin::symmetric(6, 2))
                                .show(ui, |ui| {
                                    ui.label(RichText::new("Activo").color(GREEN_STABLE).size(10.0));
                                });
                        }
                    });
                    ui.label(RichText::new(&mon.label).color(TEXT_PRIMARY).size(14.0).strong());
                    ui.label(
                        RichText::new(format!("{} · {} Hz", mon.resolution, mon.hz))
                            .color(TEXT_SECONDARY)
                            .size(12.0),
                    );
                });

                ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
                    let toggle_label = if mon.active { "Desactivar" } else { "Activar" };
                    let color = if mon.active { egui::Color32::from_rgb(185, 28, 28) } else { GREEN_STABLE };
                    let btn = egui::Button::new(RichText::new(toggle_label).color(color).size(12.0))
                        .stroke(egui::Stroke::new(1.0, color))
                        .fill(egui::Color32::TRANSPARENT)
                        .corner_radius(6.0);
                    if ui.add(btn).clicked() {
                        mon.active = !mon.active;
                    }
                    ui.add_space(8.0);
                    if ui.button(RichText::new("⚙").color(TEXT_SECONDARY).size(14.0)).clicked() {}
                });
            });
        });
        ui.add_space(8.0);
    }

    ui.add_space(8.0);

    // Botón añadir monitor
    let add_btn = egui::Button::new(
        RichText::new("＋  Agregar monitor virtual").color(ACCENT_CYAN).size(13.0),
    )
    .fill(BG_SURFACE)
    .stroke(egui::Stroke::new(1.0, ACCENT_CYAN))
    .corner_radius(8.0)
    .min_size(Vec2::new(ui.available_width(), 40.0));

    if ui.add(add_btn).clicked() {}
}
