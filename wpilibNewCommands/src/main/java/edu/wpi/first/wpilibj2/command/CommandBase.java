// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.wpilibj2.command;

import static edu.wpi.first.util.ErrorMessages.requireNonNullParam;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import java.util.HashSet;
import java.util.Set;

/**
 * A {@link Sendable} base class for {@link Command}s.
 *
 * <p>This class is provided by the NewCommands VendorDep
 */
public abstract class CommandBase implements Sendable, Command {
  protected Set<Subsystem> m_requirements = new HashSet<>();

  protected CommandBase() {
    String name = getClass().getName();
    SendableRegistry.add(this, name.substring(name.lastIndexOf('.') + 1));
  }

  /**
   * Adds the specified subsystems to the requirements of the command. The scheduler will prevent
   * two commands that require the same subsystem from being scheduled simultaneously.
   *
   * <p>Note that the scheduler determines the requirements of a command when it is scheduled, so
   * this method should normally be called from the command's constructor.
   *
   * @param requirements the requirements to add
   */
  public final void addRequirements(Subsystem... requirements) {
    for (Subsystem requirement : requirements) {
      m_requirements.add(requireNonNullParam(requirement, "requirement", "addRequirements"));
    }
  }

  @Override
  public Set<Subsystem> getRequirements() {
    return m_requirements;
  }

  @Override
  public String getName() {
    return SendableRegistry.getName(this);
  }

  /**
   * Sets the name of this Command.
   *
   * @param name name
   */
  @Override
  public void setName(String name) {
    SendableRegistry.setName(this, name);
  }

  /**
   * Gets the subsystem name of this Command.
   *
   * @return Subsystem name
   */
  public String getSubsystem() {
    return SendableRegistry.getSubsystem(this);
  }

  /**
   * Sets the subsystem name of this Command.
   *
   * @param subsystem subsystem name
   */
  public void setSubsystem(String subsystem) {
    SendableRegistry.setSubsystem(this, subsystem);
  }

  /**
   * Initializes this sendable. Useful for allowing implementations to easily extend SendableBase.
   *
   * @param builder the builder used to construct this sendable
   */
  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Command");
    builder.addStringProperty(".name", this::getName, null);
    builder.addBooleanProperty(
        "running",
        this::isScheduled,
        value -> {
          if (value) {
            if (!isScheduled()) {
              schedule();
            }
          } else {
            if (isScheduled()) {
              cancel();
            }
          }
        });
    builder.addBooleanProperty(
        ".isParented", () -> CommandScheduler.getInstance().isComposed(this), null);
    builder.addStringProperty(
        "interruptBehavior", () -> getInterruptionBehavior().toString(), null);
    builder.addBooleanProperty("runsWhenDisabled", this::runsWhenDisabled, null);
  }
}
