package frc.robot.commands;


import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends Command {


    private final Shooter shooter;


    private final Timer stateTimer = new Timer();


    private enum ShooterState {
        PRESSURIZING,
        PRESSURIZED,
        FIRING,
        FINISHED,
        FAULT
    }

    private ShooterState state;

    public ShooterCommand(Shooter shooter) {
        this.shooter = shooter;



        addRequirements(shooter);

    }

    @Override
    public void initialize() {
        setState(ShooterState.PRESSURIZING);
    }

    @Override
    public void execute() {
        switch (state) {
            case PRESSURIZING:
                if (stateTimer.hasElapsed(Constants.ShooterConstants.kPressurizingTimeout)) {
                    setState(ShooterState.PRESSURIZED);
                }
                break;
            case PRESSURIZED:
                setState(ShooterState.FIRING);
                break;
            case FIRING:
                if (stateTimer.hasElapsed(Constants.ShooterConstants.kFiringTimeout)) {
                    setState(ShooterState.FINISHED);
                }
                break;
            case FINISHED:
                break;
            case FAULT:
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return state == ShooterState.FINISHED || state == ShooterState.FAULT;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.closeSeal();
        shooter.closeChamber();
    }

    private void setState(ShooterState newState) {
        state = newState;
        stateTimer.restart();

        switch (newState) {
            case PRESSURIZING:
                shooter.closeSeal();
                shooter.openLeftChamber();
                shooter.openRightChamber();
                break;
            case PRESSURIZED:
                shooter.closeChamber();
                break;
            case FIRING:
                shooter.openSeal();
                break;
            case FINISHED:
                shooter.closeSeal();
                shooter.closeChamber();
                break;
            case FAULT:
                shooter.closeSeal();
                shooter.closeChamber();
                break;

        }
    }
}