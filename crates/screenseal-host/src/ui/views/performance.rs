/// Vista "Rendimiento" — métricas en tiempo real
use egui::{RichText, Ui, Vec2};

use crate::ui::{theme::*, widgets::*};

pub fn render(ui: &mut Ui, fps: u32, latency_ms: u32, dropouts: u32, buffers: u32) {
    ui.heading(RichText::new("Rendimiento").color(TEXT_PRIMARY).size(18.0));
    ui.label(RichText::new("Métricas de sesión en tiempo real").color(TEXT_MUTED).size(12.0));
    ui.add_space(16.0);

    // ── KPI grid 2×2 ─────────────────────────────────────────────────────────
    ui.columns(2, |cols| {
        kpi_card(&mut cols[0], &format!("{} ms", latency_ms), "Latencia", "ultra-baja", GREEN_STABLE);
        kpi_card(&mut cols[1], &fps.to_string(), "FPS", "estable", GREEN_STABLE);
    });
    ui.add_space(8.0);
    ui.columns(2, |cols| {
        kpi_card(&mut cols[0], "320 Mbps", "Ancho de banda", "USB 3.0", ACCENT_CYAN);
        kpi_card(&mut cols[1], &dropouts.to_string(), "Dropouts", "total sesión", GREEN_STABLE);
    });

    ui.add_space(16.0);

    // ── Barras de rendimiento ─────────────────────────────────────────────────
    card(ui, |ui| {
        ui.label(RichText::new("Canal de transferencia").color(TEXT_PRIMARY).size(14.0).strong());
        ui.add_space(10.0);

        perf_bar(ui, "USB",     0.85, "320 Mbps", ACCENT_CYAN);
        ui.add_space(8.0);
        perf_bar(ui, "CPU enc", 0.22, "22%",      GREEN_STABLE);
        ui.add_space(8.0);
        perf_bar(ui, "GPU enc", 0.15, "15%",      GREEN_STABLE);
    });

    ui.add_space(12.0);

    // ── Info de sesión ────────────────────────────────────────────────────────
    card(ui, |ui| {
        ui.label(RichText::new("Sesión actual").color(TEXT_PRIMARY).size(14.0).strong());
        ui.add_space(10.0);
        info_row(ui, "Inicio de sesión",   "18:10:03");
        info_row(ui, "Tiempo activo",      "00:14:27");
        info_row(ui, "Datos transferidos", "1.2 GB");
        info_row(ui, "Modo de conexión",   "USB Directo");
        info_row(ui, "Buffers en vuelo",   &buffers.to_string());
    });
}

fn kpi_card(ui: &mut Ui, value: &str, label: &str, sub: &str, color: egui::Color32) {
    card(ui, |ui| {
        ui.set_min_size(Vec2::new(ui.available_width(), 80.0));
        ui.label(RichText::new(value).color(color).size(24.0).strong());
        ui.label(RichText::new(label).color(TEXT_SECONDARY).size(12.0));
        ui.label(RichText::new(sub).color(color).size(11.0));
    });
}

fn perf_bar(ui: &mut Ui, label: &str, fraction: f32, value_label: &str, color: egui::Color32) {
    ui.horizontal(|ui| {
        ui.add_sized(
            [60.0, 0.0],
            egui::Label::new(RichText::new(label).color(TEXT_SECONDARY).size(12.0)),
        );
        ui.add(
            egui::ProgressBar::new(fraction)
                .fill(color)
                .desired_width(ui.available_width() - 60.0),
        );
        ui.add_space(8.0);
        ui.label(RichText::new(value_label).color(color).size(12.0));
    });
}

fn info_row(ui: &mut Ui, label: &str, value: &str) {
    ui.horizontal(|ui| {
        ui.label(RichText::new(label).color(TEXT_SECONDARY).size(13.0));
        ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
            ui.label(RichText::new(value).color(TEXT_PRIMARY).size(13.0).strong());
        });
    });
    ui.add_space(4.0);
}
