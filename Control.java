package org.firstinspires.ftc.teamcode;

public class Control {
    private DcMotor shooterTop;
    private DcMotor shooterBottom;
    private CRServo intaky;
    private CRServo moreintaky;

    public Control(DcMotor shooterTop, DcMotor shooterBottom, CRServo intaky, CRServo moreintaky) {
        this.shooterTop = shooterTop;
        this.shooterBottom = shooterBottom;
        this.intaky = intaky;
        this.moreintaky = moreintaky;
    }

    public void shoot() {
    }

    public void startIntake(String intake) {
        if (intaky.equals("intakeIn")) {
            intaky.setPower(1);
            moreintaky.setPower(1);
        } else if (intake.equals("intakeOut")) {
            intaky.setPower(-1);
            moreintaky.setPower(-1);
        }
    }
    public void startFlyweel(String distance) {
        if (distance.equals("near")) {
            //start motors
            shooterTop.setPower(0.30);
            shooterBottom.setPower(0.40);

        } else if (distance.equals("far")) {
            shooterTop.setPower(50);
            shooterBottom.setPower(60);
        }

    }

    public void stopFlyweel() {

    }
}