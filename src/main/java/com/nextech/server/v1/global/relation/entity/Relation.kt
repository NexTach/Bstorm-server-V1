package com.nextech.server.v1.global.relation.entity

import com.nextech.server.v1.domain.members.entity.Members
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "relations")
data class Relation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id", nullable = false)
    val id: Long,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "from_protected", referencedColumnName = "phone_number", nullable = false)
    val fromProtected: Members,

    @ElementCollection
    @CollectionTable(name = "relation_to_ward", joinColumns = [JoinColumn(name = "relation_id")])
    @Column(name = "to_ward", nullable = false)
    val toWard: List<String>
)