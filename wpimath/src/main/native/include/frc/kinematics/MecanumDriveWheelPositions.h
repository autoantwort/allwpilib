// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

#pragma once

#include <wpi/MathExtras.h>
#include <wpi/SymbolExports.h>

#include "units/length.h"

namespace frc {
/**
 * Represents the wheel positions for a mecanum drive drivetrain.
 */
struct WPILIB_DLLEXPORT MecanumDriveWheelPositions {
  /**
   * Distance driven by the front-left wheel.
   */
  units::meter_t frontLeft = 0_m;

  /**
   * Distance driven by the front-right wheel.
   */
  units::meter_t frontRight = 0_m;

  /**
   * Distance driven by the rear-left wheel.
   */
  units::meter_t rearLeft = 0_m;

  /**
   * Distance driven by the rear-right wheel.
   */
  units::meter_t rearRight = 0_m;

  /**
   * Checks equality between this MecanumDriveWheelPositions and another object.
   *
   * @param other The other object.
   * @return Whether the two objects are equal.
   */
  bool operator==(const MecanumDriveWheelPositions& other) const = default;

  /**
   * Checks inequality between this MecanumDriveWheelPositions and another
   * object.
   *
   * @param other The other object.
   * @return Whether the two objects are not equal.
   */
  bool operator!=(const MecanumDriveWheelPositions& other) const = default;

  MecanumDriveWheelPositions operator-(
      const MecanumDriveWheelPositions& other) const {
    return {frontLeft - other.frontLeft, frontRight - other.frontRight,
            rearLeft - other.rearLeft, rearRight - other.rearRight};
  }

  MecanumDriveWheelPositions Interpolate(
      const MecanumDriveWheelPositions& endValue, double t) const {
    return {wpi::Lerp(frontLeft, endValue.frontLeft, t),
            wpi::Lerp(frontRight, endValue.frontRight, t),
            wpi::Lerp(rearLeft, endValue.rearLeft, t),
            wpi::Lerp(rearRight, endValue.rearRight, t)};
  }
};
}  // namespace frc
