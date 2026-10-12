package frc.robot.commands;


import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends Command {


    private final Shooter shooter;


    private final Timer stateTimer = new Timer();
    private final Timer commandTimer = new Timer();


    private enum ShooterState {
        PRESSURIZING,
        FIRING,
        FINISHED,
        FAULT
    }

    private ShooterState state;
    // private ShooterState previousState;

    public ShooterCommand(Shooter shooter) {
        this.shooter = shooter;



        addRequirements(shooter);

    }

    @Override
    public void initialize() {
        SmartDashboard.putString("Shooter/Fault", "None");

        commandTimer.restart();
        setState(ShooterState.PRESSURIZING);
    }

    @Override
    public void execute() {
        if (state != ShooterState.FINISHED && state != ShooterState.FAULT && commandTimer.hasElapsed(Constants.ShooterConstants.kMaxCommandDuration)) {
            SmartDashboard.putString("Shooter/Fault", "Command Timeout");
            
            setState(ShooterState.FAULT);
            return;
        }
        switch (state) {
            case PRESSURIZING:
                if (stateTimer.hasElapsed(Constants.ShooterConstants.kPressurizingDuration)) {
                    setState(ShooterState.FIRING);
                }
                break;
            case FIRING:
                if (stateTimer.hasElapsed(Constants.ShooterConstants.kFiringDuration)) {
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
        commandTimer.stop();
        stateTimer.stop();
        
        shooter.closeChamber();
    }

    private void setState(ShooterState newState) {
        state = newState;
        SmartDashboard.putString("Shooter/State", newState.toString());
        stateTimer.restart();
        

        switch (newState) {
            case PRESSURIZING:
                shooter.closeChamber();
                break;
            case FIRING:
                shooter.openChamber();
                break;
            case FINISHED:
                shooter.closeChamber();
                break;
            case FAULT:
                shooter.closeChamber();
                break;

        }
    }
}