package org.firstinspires.ftc.teamcode.robotOpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.robotTemplates.WestCoast15;
import org.firstinspires.ftc.teamcode.robotTemplates.TankDT;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;

/**
 * Created by Frank Portman on 5/25/2018
 */
@TeleOp(name = "WCD15 Test Drive", group = "Test Drive")
public class WestCoast15TestDrive extends WestCoast15 {
    boolean switchControlState    = false,
            canSwitchControlState = false,
            switchTurnState       = false,
            canSwitchTurnState    = false;
    double  rt,
            lt;
    @Override
    public void init(){
        super.init();
        controlState = TankDT.ControlState.ARCADE;
        direction = TankDT.Direction.FOR;
        activateGamepad1();
        activateGamepad2();
        setSpeed(0.5);
    }
    @Override
    public void start(){super.start();
    }
    @Override
    public void loop(){
        rt = gamepad1.right_trigger;
        super.loop();
        switch(controlState){
            case ARCADE:
                if(switchControlState){
                    controlState = ControlState.FIELD_CENTRIC;
                    switchControlState = false;
                    canSwitchControlState = false;
                    directionMult = 1;
                }
                else if(rt < UniversalConstants.Triggered.TRIGGER){
                    switchControlState = false;
                    canSwitchControlState = true;
                }
                else if(rt > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                    switchControlState = true;
                break;
            case FIELD_CENTRIC:
                lt = gamepad1.left_trigger;
                switch(turnState){
                    case FAST:
                        if(switchTurnState){
                            turnState = FCTurnState.SMOOTH;
                            switchTurnState = false;
                            canSwitchTurnState = false;
                        }
                        else if(lt < UniversalConstants.Triggered.TRIGGER){
                            switchTurnState = false;
                            canSwitchTurnState = true;
                        }
                        else if(lt > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                            switchTurnState = true;
                        break;
                    case SMOOTH:
                        if(switchTurnState){
                            turnState = FCTurnState.FAST;
                            switchTurnState = false;
                            canSwitchTurnState = false;
                        }
                        else if(lt < UniversalConstants.Triggered.TRIGGER){
                            switchTurnState = false;
                            canSwitchTurnState = true;
                        }
                        else if(lt > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                            switchTurnState = true;
                        break;
                }
                if(switchControlState){
                    controlState = ControlState.TANK;
                    switchControlState = false;
                    canSwitchControlState = false;
                }
                else if(rt < UniversalConstants.Triggered.TRIGGER){
                    switchControlState = false;
                    canSwitchControlState = true;
                }
                else if(rt > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                    switchControlState = true;
                break;
            case TANK:
                if(switchControlState){
                    controlState = ControlState.ARCADE;
                    switchControlState = false;
                    canSwitchControlState = false;
                    directionMult = 1;
                }
                else if(rt < UniversalConstants.Triggered.TRIGGER){
                    switchControlState = false;
                    canSwitchControlState = true;
                }
                else if(rt > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                    switchControlState = true;
                break;
        }
    }
}