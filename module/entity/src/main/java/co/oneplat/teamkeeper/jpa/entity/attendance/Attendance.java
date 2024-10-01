/*
 * Copyright 2024 OnePlat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.oneplat.teamkeeper.jpa.entity.attendance;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import co.oneplat.teamkeeper.jpa.entity.base.AbstractDeletableEntity;
import co.oneplat.teamkeeper.jpa.entity.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ATTENDANCE", uniqueConstraints = @UniqueConstraint(columnNames = {"user", "date"}))
public class Attendance extends AbstractDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTEND_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private User user;

    @Column(name = "ATTEND_DATE", nullable = false, updatable = false)
    private LocalDate date;

    @Column(name = "ATTEND_START_TIME")
    private LocalTime startTime;

    @Column(name = "ATTEND_END_TIME")
    private LocalTime endTime;

}
