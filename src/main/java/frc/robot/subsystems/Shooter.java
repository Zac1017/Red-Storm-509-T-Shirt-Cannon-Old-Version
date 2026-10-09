package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    
    public final Solenoid chamberTankSolenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, 1);
    public final Solenoid kSealSolenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, 2);

    public Shooter() {
        closeSeal();
        closeChamber();
    }

    public void open(Solenoid solenoid) {
        solenoid.set(true);
    }

    public void close(Solenoid solenoid) {
        solenoid.set(false);
    }

    // public boolean isAtPressure(double pressureThreshold) {
    //     double pressure = pressureSensor.getPressure(); 
    //     return pressure >= pressureThreshold;
    // }

    // public double getPressure() {
    //     return pressureSensor.getPressure();
    // }

    public void openChamber() {
        open(chamberTankSolenoid);
    }

    public void closeChamber() {
        close(chamberTankSolenoid);
    }

    public void closeSeal() {
        close(kSealSolenoid);
    }

    public void openSeal() {
        open(kSealSolenoid);
    }

}

