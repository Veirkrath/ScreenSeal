mod transport;
mod ui;

use eframe::{egui, NativeOptions};
use egui::{Color32, Frame, Margin, RichText, ScrollArea, Stroke, Vec2, ViewportBuilder};

use ui::{
    state::{ActiveTab, AppState},
    theme::*,
    views::{
        devices::{DevicesState, render as render_devices},
        performance::render as render_performance,
        quick_connect::render as render_quick_connect,
        virtual_monitors::{VirtualMonitorsState, render as render_virtual_monitors},
    },
    widgets::{nav_tab, pro_badge, status_badge},
};

// ─── App principal ────────────────────────────────────────────────────────────

struct ScreenSealApp {
    state:            AppState,
    vm_state:         VirtualMonitorsState,
    devices_state:    DevicesState,
}

impl Default for ScreenSealApp {
    fn default() -> Self {
        Self {
            state:         AppState::default(),
            vm_state:      VirtualMonitorsState::default(),
            devices_state: DevicesState::default(),
        }
    }
}

impl eframe::App for ScreenSealApp {
    fn update(&mut self, ctx: &egui::Context, _frame: &mut eframe::Frame) {
        // Estilo global
        apply_global_style(ctx);

        // ── Top bar ───────────────────────────────────────────────────────────
        egui::TopBottomPanel::top("top_bar")
            .exact_height(46.0)
            .frame(
                Frame::new()
                    .fill(BG_CARD)
                    .inner_margin(Margin::symmetric(16, 8))
                    .stroke(Stroke::new(1.0, BORDER_SUBTLE)),
            )
            .show(ctx, |ui| {
                ui.horizontal(|ui| {
                    // Logo
                    ui.label(RichText::new("ScreenSeal").color(TEXT_PRIMARY).size(15.0).strong());
                    pro_badge(ui);
                    ui.label(RichText::new("DESKTOP PRO").color(TEXT_MUTED).size(9.0));

                    ui.add_space(20.0);

                    // Tabs de navegación
                    let tabs = [
                        (ActiveTab::QuickConnect,      "Conexión Rápida"),
                        (ActiveTab::VirtualMonitors,   "Monitores Virtuales"),
                        (ActiveTab::Performance,       "Rendimiento"),
                        (ActiveTab::Devices,           "Dispositivos"),
                    ];
                    for (tab, label) in &tabs {
                        if nav_tab(ui, label, self.state.active_tab == *tab).clicked() {
                            self.state.active_tab = tab.clone();
                        }
                        ui.add_space(2.0);
                    }

                    // Derecha: badges de enlace + avatar
                    ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
                        // Avatar
                        Frame::new()
                            .fill(ACCENT_PURPLE)
                            .corner_radius(18.0)
                            .inner_margin(Margin::same(6))
                            .show(ui, |ui| {
                                ui.label(RichText::new("U").color(Color32::WHITE).size(13.0).strong());
                            });
                        ui.add_space(8.0);
                        status_badge(ui, "ENLACE USB LISTO", GREEN_DOT, GREEN_STABLE);
                        ui.add_space(8.0);
                        status_badge(ui, "ENLACE USB LISTO", GREEN_DOT, GREEN_STABLE);
                    });
                });
            });

        // ── Status bar inferior ────────────────────────────────────────────────
        egui::TopBottomPanel::bottom("status_bar")
            .exact_height(26.0)
            .frame(
                Frame::new()
                    .fill(BG_CARD)
                    .inner_margin(Margin::symmetric(16, 4))
                    .stroke(Stroke::new(1.0, BORDER_SUBTLE)),
            )
            .show(ctx, |ui| {
                ui.horizontal(|ui| {
                    ui.label(
                        RichText::new(format!(
                            "Motor H.265 Ultra-Low Latency  |  Buffers: {} frames  |  Cifrado TLS 1.3 Local",
                            self.state.buffers
                        ))
                        .color(TEXT_MUTED)
                        .size(10.5),
                    );
                    ui.with_layout(egui::Layout::right_to_left(egui::Align::Center), |ui| {
                        ui.label(
                            RichText::new("● ScreenSeal v2.4.0-pro (Build 8821)")
                                .color(GREEN_DOT)
                                .size(10.5),
                        );
                    });
                });
            });

        // ── Panel central (contenido) ─────────────────────────────────────────
        egui::CentralPanel::default()
            .frame(Frame::new().fill(BG_DEEP).inner_margin(Margin::same(16)))
            .show(ctx, |ui| {
                ScrollArea::vertical().show(ui, |ui| {
                    match self.state.active_tab {
                        ActiveTab::QuickConnect =>
                            render_quick_connect(ui, &mut self.state),
                        ActiveTab::VirtualMonitors =>
                            render_virtual_monitors(ui, &mut self.vm_state),
                        ActiveTab::Performance =>
                            render_performance(
                                ui,
                                self.state.fps,
                                self.state.latency_ms,
                                self.state.dropouts,
                                self.state.buffers,
                            ),
                        ActiveTab::Devices =>
                            render_devices(ui, &mut self.devices_state),
                    }
                });
            });
    }
}

// ─── Estilo global ────────────────────────────────────────────────────────────

fn apply_global_style(ctx: &egui::Context) {
    let mut style = (*ctx.style()).clone();

    // Colores base
    style.visuals.panel_fill           = BG_DEEP;
    style.visuals.window_fill          = BG_CARD;
    style.visuals.extreme_bg_color     = BG_SURFACE;
    style.visuals.faint_bg_color       = BG_SURFACE;
    style.visuals.code_bg_color        = BG_SURFACE;
    style.visuals.override_text_color  = Some(TEXT_PRIMARY);

    // Widgets
    style.visuals.widgets.inactive.bg_fill        = BG_SURFACE;
    style.visuals.widgets.inactive.fg_stroke      = Stroke::new(1.0_f32, BORDER_SUBTLE);
    style.visuals.widgets.hovered.bg_fill         = BG_SURFACE2;
    style.visuals.widgets.hovered.fg_stroke       = Stroke::new(1.5_f32, ACCENT_CYAN);
    style.visuals.widgets.active.bg_fill          = ACCENT_PURPLE;
    style.visuals.widgets.active.fg_stroke        = Stroke::new(1.5_f32, ACCENT_CYAN);
    style.visuals.widgets.noninteractive.bg_fill  = BG_CARD;
    style.visuals.widgets.noninteractive.fg_stroke = Stroke::new(1.0_f32, BORDER_SUBTLE);

    // Bordes de ventana
    style.visuals.window_stroke = Stroke::new(1.0_f32, BORDER_SUBTLE);

    // Spacing
    style.spacing.item_spacing   = Vec2::new(8.0, 6.0);
    style.spacing.button_padding = Vec2::new(10.0, 6.0);

    ctx.set_style(style);
}

// ─── Entry point ─────────────────────────────────────────────────────────────

fn main() -> eframe::Result {
    let native_options = NativeOptions {
        viewport: ViewportBuilder::default()
            .with_title("ScreenSeal — Desktop Pro")
            .with_inner_size([1100.0, 700.0])
            .with_min_inner_size([900.0, 600.0])
            .with_icon(eframe::icon_data::from_png_bytes(
                include_bytes!("../assets/icon.png")
            ).unwrap_or_default()),
        ..Default::default()
    };

    eframe::run_native(
        "ScreenSeal",
        native_options,
        Box::new(|_cc| Ok(Box::new(ScreenSealApp::default()))),
    )
}