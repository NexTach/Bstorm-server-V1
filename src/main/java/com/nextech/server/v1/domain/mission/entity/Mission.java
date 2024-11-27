package com.nextech.server.v1.domain.mission.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;
import com.nextech.server.v1.domain.mission.dto.enums.Status;
import com.nextech.server.v1.global.members.entity.Members;

@Entity
@Table(name = "missions")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "from_member_id", nullable = false)
    private Members from;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "to_ward_member_id", nullable = false)
    @Setter
    private Members toWard;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mission_status", joinColumns = @JoinColumn(name = "mission_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Setter
    private Set<Status> status;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "expiration_date", nullable = false)
    @Setter
    private LocalDateTime expirationDate;

    @Column(name = "title", nullable = false)
    @Setter
    private String title;

    @Column(name = "content", nullable = false)
    @Setter
    private String content;

    @PreRemove
    private void preRemove() {
        from.getMission().remove(this);
        toWard.getMission().remove(this);
    }
}
