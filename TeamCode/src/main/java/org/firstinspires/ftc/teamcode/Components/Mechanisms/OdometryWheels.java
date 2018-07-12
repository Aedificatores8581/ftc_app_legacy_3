package org.firstinspires.ftc.teamcode.Components.Mechanisms;

import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.robotUniversal.Position;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;

public class OdometryWheels {
    public class Wheels3{
        public double encPerInch;
        //the angles are the angles of the motors, not their wheels
        public Position wheel1 = new Position();
        public Position wheel2 = new Position();
        public Position wheel3 = new Position();
        public Wheels3(Position p1, Position p2, Position p3){
            wheel1 = p1;
            wheel2 = p2;
            wheel3 = p3;
        }
        public Position standardPositionTrack(Position currentPos, double read1, double read2, double read3){
            double r = read1, l = read2, x = read3;
            double xDiff = wheel1.radius() / wheel2.radius();
            double angle = (r / Math.cos(wheel1.angleOfVector() - wheel1.angle) - l / Math.cos(wheel2.angleOfVector() - wheel2.angle) * xDiff) / ((wheel1.radius() * 2) * encPerInch);
            x -= angle * wheel3.radius() * Math.cos(wheel3.angleOfVector() - wheel3.angle);
            r -= angle * wheel1.radius() * Math.cos(wheel1.angleOfVector() - wheel1.angle);
            l -= angle * wheel2.radius() * Math.cos(wheel2.angleOfVector() - wheel2.angle);
            //l and r should be equal
            Vector2 velocity = new Vector2(x, l);
            Vector2 vel2 = new Vector2();
            if (angle != 0) {
                double rad = velocity.magnitude() / angle;
                vel2.setFromPolar(rad, angle);
                vel2.x -= rad;
                vel2.rotate(velocity.angle());
            }
            else
                vel2 = velocity;
            currentPos.x += vel2.x;
            currentPos.y += vel2.y;
            currentPos.angle += angle;
            return currentPos;
        }
    }
}
