package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

                                                           //module is can id
                                                           //channel is port on pdh
    // public final Solenoid chamberTankSolenoid = new Solenoid(3, PneumaticsModuleType.CTREPCM, 4);
    public final SparkMax chamberTankSolenoid = new SparkMax(3, MotorType.kBrushed);

    public Shooter() {
        
        SparkMaxConfig solenoidConfig = new SparkMaxConfig();

       solenoidConfig.smartCurrentLimit(40).
            idleMode(IdleMode.kCoast).
            inverted(true);

        closeChamber();


    }

    public void open(SparkMax motor) {
        motor.setVoltage(12);
        SmartDashboard.putBoolean("Shooter", true);
    }

    public void close(SparkMax motor) {
        motor.setVoltage(0);
        SmartDashboard.putBoolean("Shooter", false);
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

    public void closeSeal(){}
    public void closeChamber() {
        close(chamberTankSolenoid);
    }

    
}

