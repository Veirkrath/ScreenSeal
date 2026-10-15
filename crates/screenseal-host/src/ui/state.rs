/// Datos de estado de la UI (sin lógica real — solo vistas)
#[derive(Debug, Clone)]
pub struct DeviceInfo {
    pub name: String,
    pub model_tag: String,
    pub connection: String,
    pub latency_ms: u32,
    pub stability: String,
}

#[derive(Debug, Clone, PartialEq)]
pub enum ProjectionMode {
    Extended,
    Mirror,
}

#[derive(Debug, Clone, PartialEq)]
pub enum PhonePosition {
    Left,
    Right,
    Top,
}

#[derive(Debug, Clone, PartialEq)]
pub enum ActiveTab {
    QuickConnect,
    VirtualMonitors,
    Performance,
    Devices,
}

/// Estado global de la aplicación (stub para las vistas)
pub struct AppState {
    pub active_tab: ActiveTab,
    pub device: DeviceInfo,
    pub projection_mode: ProjectionMode,
    pub phone_position: PhonePosition,
    pub send_audio: bool,
    pub minimize_to_tray: bool,
    pub is_connected: bool,
    pub fps: u32,
    pub resolution: String,
    pub buffers: u32,
    pub latency_ms: u32,
    pub dropouts: u32,
}

impl Default for AppState {
    fn default() -> Self {
        Self {
            active_tab: ActiveTab::QuickConnect,
            device: DeviceInfo {
                name: "Samsung Galaxy S24 Ultra".to_string(),
                model_tag: "Receptor".to_string(),
                connection: "Enlace USB Directo · 100% Estable sin cortes".to_string(),
                latency_ms: 2,
                stability: "100% Estable".to_string(),
            },
            projection_mode: ProjectionMode::Extended,
            phone_position: PhonePosition::Right,
            send_audio: true,
            minimize_to_tray: true,
            is_connected: true,
            fps: 60,
            resolution: "1080 × 2400".to_string(),
            buffers: 0,
            latency_ms: 2,
            dropouts: 0,
        }
    }
}
