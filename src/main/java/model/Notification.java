package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
public class Notification implements Serializable {
    private String content;

    @Column(name = "received_date")
    private LocalDateTime receivedData;

    @Column(name = "is_read")
    private boolean isRead;

}
