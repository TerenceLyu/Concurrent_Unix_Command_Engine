package cs131.pa2.CarsTunnels;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Direction;

import java.util.Iterator;
import java.util.LinkedList;

public class BasicTunnel extends Tunnel{
	private Direction tunnelDirection;
	private LinkedList<Vehicle> lane = new LinkedList<>();
	private int carCount = 0;
	private int sledCount = 0;
	public BasicTunnel(String name) {
		super(name);
	}

	@Override
	public synchronized boolean tryToEnterInner(Vehicle vehicle) {
		if (this.tunnelDirection == null){
			this.tunnelDirection = vehicle.getDirection();
		}
		if (this.tunnelDirection == vehicle.getDirection()){
			
			if (vehicle.toString().contains("CAR")){
				//check for car and sled
				if (this.sledCount == 0 && this.carCount < 3){
					//if no sled and less than 3 cars
					this.lane.add(vehicle);
					this.carCount++;
					return true;
				}
			}else if (vehicle.toString().contains("SLED")){
				//check for car and sled
				if (this.carCount == 0 && this.sledCount == 0){
					//if no car and no sled
					this.lane.add(vehicle);
					this.sledCount++;
					return true;
				}
			}else {
				//Ambulance, no need to check
				this.lane.add(vehicle);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public synchronized void exitTunnelInner(Vehicle vehicle) {
		this.lane.remove(vehicle);
		if (vehicle.toString().contains("CAR")){
			this.carCount--;
		}else if (vehicle.toString().contains("SLED")){
			this.sledCount--;
		}
		if (this.lane.size() == 0){
			this.tunnelDirection = null;
		}
	}
	
}
