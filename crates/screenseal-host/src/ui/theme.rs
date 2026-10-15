/// Paleta de colores ScreenSeal — espejo del tema Android
use egui::Color32;

pub const BG_DEEP: Color32        = Color32::from_rgb(10,  14,  26);   // fondo principal
pub const BG_CARD: Color32        = Color32::from_rgb(17,  24,  39);   // tarjetas
pub const BG_SURFACE: Color32     = Color32::from_rgb(26,  34,  54);   // superficies
pub const BG_SURFACE2: Color32    = Color32::from_rgb(20,  28,  45);   // inputs / hover

pub const ACCENT_CYAN: Color32    = Color32::from_rgb(0,   245, 255);  // cyan principal
pub const ACCENT_PURPLE: Color32  = Color32::from_rgb(124, 58,  237);  // violeta
pub const ACCENT_GRAD: Color32    = Color32::from_rgb(159, 103, 250);  // gradiente fin

pub const GREEN_STABLE: Color32   = Color32::from_rgb(34,  197, 94);   // conexión estable
pub const GREEN_DOT: Color32      = Color32::from_rgb(74,  222, 128);  // indicador vivo

pub const TEXT_PRIMARY: Color32   = Color32::from_rgb(226, 232, 240);  // texto principal
pub const TEXT_SECONDARY: Color32 = Color32::from_rgb(148, 163, 184);  // subtítulos
pub const TEXT_MUTED: Color32     = Color32::from_rgb(100, 116, 139);  // apagado

pub const BORDER_SUBTLE: Color32  = Color32::from_rgb(30,  45,  64);   // bordes suaves
pub const RED_DANGER: Color32     = Color32::from_rgb(239, 68,  68);   // peligro/desconectar
