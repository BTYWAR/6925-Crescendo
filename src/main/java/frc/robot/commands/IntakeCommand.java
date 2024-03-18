package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake.IntakeSubsys;
import frc.robot.subsystems.Intake.IntakeConstants.IndexerSpeed;
import frc.robot.subsystems.Intake.IntakeConstants.PivotState;

public class IntakeCommand extends Command {
    private final IntakeSubsys intake;
    private PivotState state;
    private IndexerSpeed speed;

    public IntakeCommand(IntakeSubsys intake, PivotState state, IndexerSpeed speed) {
        this.intake = intake;
        this.state = state;
        this.speed = speed;
        
        addRequirements(intake);
    }

    public IntakeCommand(IntakeSubsys intake, PivotState state) {
        this(intake, state, null);
    }

    public IntakeCommand(IntakeSubsys intake, IndexerSpeed speed) {
        this(intake, null, speed);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (state != null) intake.intakePivot(state.pivotSetpoint);
        if (speed != null) intake.setIndexerSpeed(speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intake.setIndexerSpeed(IndexerSpeed.NONE);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // TODO: add check for stall + add finish condition for pivot
        // maybe pg 17 of https://store.ctr-electronics.com/content/user-manual/Falcon%20500%20v3%20User%27s%20Guide.pdf
        return intake.indexerMotor.get() >= speed.speed;
    }
}