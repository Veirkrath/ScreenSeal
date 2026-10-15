/// Widgets reutilizables con el estilo ScreenSeal
use egui::{Color32, Frame, Margin, Response, RichText, Rounding, Sense, Stroke, Ui, Vec2};

use super::theme::*;

// ─── Card / panel con fondo oscuro ───────────────────────────────────────────

pub fn card<R>(ui: &mut Ui, content: impl FnOnce(&mut Ui) -> R) -> R {
    Frame::new()
        .fill(BG_CARD)
        .corner_radius(12.0)
        .inner_margin(Margin::same(16))
        .stroke(Stroke::new(1.0, BORDER_SUBTLE))
        .show(ui, content)
        .inner
}

pub fn surface_box<R>(ui: &mut Ui, content: impl FnOnce(&mut Ui) -> R) -> R {
    Frame::new()
        .fill(BG_SURFACE)
        .corner_radius(8.0)
        .inner_margin(Margin::same(10))
        .show(ui, content)
        .inner
}

// ─── Botón principal con gradiente simulado ───────────────────────────────────

/// Botón primario (relleno cyan→purple). Devuelve true si fue presionado.
pub fn primary_button(ui: &mut Ui, label: &str) -> bool {
    let desired_size = Vec2::new(ui.available_width(), 44.0);
    let (rect, response) = ui.allocate_exact_size(desired_size, Sense::click());

    if ui.is_rect_visible(rect) {
        let painter = ui.painter();

        // Gradiente simulado: fondo cyan con stroke purple
        let rounding = Rounding::same(10);
        painter.rect_filled(rect, rounding, ACCENT_CYAN);

        // Banda derecha purple para simular gradiente
        let half = egui::Rect::from_min_max(
            egui::pos2(rect.center().x, rect.min.y),
            rect.max,
        );
        painter.rect_filled(half, rounding, ACCENT_PURPLE);

        // Overlay semitransparente para blend suave
        painter.rect_filled(rect, rounding, Color32::from_rgba_unmultiplied(0, 245, 255, 40));

        // Texto centrado
        painter.text(
            rect.center(),
            egui::Align2::CENTER_CENTER,
            label,
            egui::FontId::proportional(15.0),
            Color32::WHITE,
        );

        if response.hovered() {
            painter.rect_stroke(rect, rounding, Stroke::new(1.5, ACCENT_CYAN), egui::StrokeKind::Outside);
        }
    }

    response.clicked()
}

/// Botón secundario con borde (outline).
pub fn outline_button(ui: &mut Ui, label: &str, border_color: Color32) -> bool {
    let desired_size = Vec2::new(ui.available_width(), 36.0);
    let (rect, response) = ui.allocate_exact_size(desired_size, Sense::click());

    if ui.is_rect_visible(rect) {
        let painter = ui.painter();
        let rounding = Rounding::same(8);
        let fill = if response.hovered() { BG_SURFACE2 } else { BG_SURFACE };

        painter.rect_filled(rect, rounding, fill);
        painter.rect_stroke(rect, rounding, Stroke::new(1.0, border_color), egui::StrokeKind::Outside);
        painter.text(
            rect.center(),
            egui::Align2::CENTER_CENTER,
            label,
            egui::FontId::proportional(13.0),
            border_color,
        );
    }

    response.clicked()
}

// ─── Badge de estado ──────────────────────────────────────────────────────────

pub fn status_badge(ui: &mut Ui, label: &str, dot_color: Color32, text_color: Color32) {
    Frame::new()
        .fill(BG_SURFACE)
        .corner_radius(20.0)
        .inner_margin(Margin::symmetric(10, 5))
        .stroke(Stroke::new(1.0, dot_color))
        .show(ui, |ui| {
            ui.horizontal(|ui| {
                ui.spacing_mut().item_spacing.x = 5.0;
                // Dot
                let (rect, _) = ui.allocate_exact_size(Vec2::splat(8.0), Sense::hover());
                ui.painter().circle_filled(rect.center(), 4.0, dot_color);
                ui.label(RichText::new(label).color(text_color).size(12.0));
            });
        });
}

// ─── Badge PRO ────────────────────────────────────────────────────────────────

pub fn pro_badge(ui: &mut Ui) {
    Frame::new()
        .fill(ACCENT_PURPLE)
        .corner_radius(4.0)
        .inner_margin(Margin::symmetric(6, 2))
        .show(ui, |ui| {
            ui.label(RichText::new("PRO").color(Color32::WHITE).size(10.0).strong());
        });
}

// ─── Separador sutil ──────────────────────────────────────────────────────────

pub fn subtle_separator(ui: &mut Ui) {
    ui.add_space(4.0);
    let stroke = Stroke::new(1.0, BORDER_SUBTLE);
    ui.painter().hline(
        ui.available_rect_before_wrap().x_range(),
        ui.cursor().top(),
        stroke,
    );
    ui.add_space(4.0);
}

// ─── Stat item (ícono + valor + sub) ─────────────────────────────────────────

pub fn stat_item(ui: &mut Ui, value: &str, sub: &str, color: Color32) {
    ui.vertical_centered(|ui| {
        ui.label(RichText::new(value).color(color).size(14.0).strong());
        ui.label(RichText::new(sub).color(TEXT_MUTED).size(11.0));
    });
}

// ─── Tab de navegación superior ──────────────────────────────────────────────

pub fn nav_tab(ui: &mut Ui, label: &str, selected: bool) -> Response {
    let text = if selected {
        RichText::new(label).color(Color32::WHITE).size(13.0).strong()
    } else {
        RichText::new(label).color(TEXT_SECONDARY).size(13.0)
    };

    let button = egui::Button::new(text)
        .fill(if selected { ACCENT_PURPLE } else { Color32::TRANSPARENT })
        .corner_radius(8.0)
        .min_size(Vec2::new(0.0, 30.0));

    ui.add(button)
}

// ─── Toggle de posición ───────────────────────────────────────────────────────

pub fn position_toggle(ui: &mut Ui, label: &str, selected: bool) -> bool {
    let btn = egui::Button::new(RichText::new(label).size(12.0).color(
        if selected { ACCENT_CYAN } else { TEXT_SECONDARY },
    ))
    .fill(if selected { BG_SURFACE } else { Color32::TRANSPARENT })
    .stroke(Stroke::new(1.0, if selected { ACCENT_CYAN } else { BORDER_SUBTLE }))
    .corner_radius(6.0);

    ui.add(btn).clicked()
}
