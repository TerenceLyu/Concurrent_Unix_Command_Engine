package cs131.pa2.CarsTunnels;

import cs131.pa2.Abstract.Log.Log;
import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;

import java.util.*;

public class PriorityScheduler extends Tunnel{
	private Collection<Tunnel> tunnels;
	private Map<Vehicle, Tunnel> map = new HashMap<>();
	private PriorityQueue<Vehicle> queue;
	public PriorityScheduler(String name) {
		super(name);
	}
	public PriorityScheduler(String name, Collection<Tunnel> tunnels, Log log){
		super(name, log);
		this.tunnels = tunnels;
		this.queue = new PriorityQueue<>(20,(a,b) -> b.getPriority()-a.getPriority());
	}
	
	@Override
	public synchronized boolean tryToEnterInner(Vehicle vehicle) {
		while (true){
			if (this.queue.size() != 0){
				if (this.queue.peek().getPriority() >= vehicle.getPriority()){
					if (!queue.contains(vehicle)){
						queue.add(vehicle);
					}
					try {
						wait();
					}catch (InterruptedException e){}
				}
			}
			
			for (Tunnel tunnel: tunnels)
			{
				if (tunnel.tryToEnterInner(vehicle)){
					queue.remove(vehicle);
					map.put(vehicle, tunnel);
					return true;
				}
			}
			
			if (!queue.contains(vehicle)){
				queue.add(vehicle);
			}
			try {
				wait();
			}catch (InterruptedException e){}
		}
		
		
	}

	@Override
	public synchronized void exitTunnelInner(Vehicle vehicle) {
		map.get(vehicle).exitTunnelInner(vehicle);
		map.remove(vehicle);
		if (queue.size()!=0){
			notify();
		}
	}
}
