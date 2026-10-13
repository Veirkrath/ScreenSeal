use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize, PartialEq)]
pub enum Message {
    Hello {
        version: u8,
    },

    HelloAck {
        version: u8,
    },
}

impl Message {
    pub fn encode(&self) -> Result<Vec<u8>, bincode::error::EncodeError> {
        bincode::serde::encode_to_vec(
            self,
            bincode::config::standard(),
        )
    }

    pub fn decode(data: &[u8]) -> Result<Message, bincode::error::DecodeError> {
        let (message, _): (Message, usize) =
            bincode::serde::decode_from_slice(
                data,
                bincode::config::standard(),
            )?;

        Ok(message)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_print_encoded() {
        let hello = Message::Hello { version: 1 };
        let hello_bytes = hello.encode().unwrap();
        println!("Hello bytes: {:?}", hello_bytes);

        let ack = Message::HelloAck { version: 1 };
        let ack_bytes = ack.encode().unwrap();
        println!("HelloAck bytes: {:?}", ack_bytes);
    }
}