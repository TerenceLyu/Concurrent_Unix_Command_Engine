package cs131.pa2.CarsTunnels;

import cs131.pa2.Abstract.Log.Log;
import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;

import java.util.*;

public class PreemptivePriorityScheduler extends Tunnel{
	private Collection<Tunnel> tunnels;
	private Map<Vehicle, Tunnel> map = new HashMap<>();
	private PriorityQueue<Vehicle> queue;
	public PreemptivePriorityScheduler(String name)
	{
		super(name);
	}
	public PreemptivePriorityScheduler(String name, Collection<Tunnel> tunnels, Log log){
		super(name, log);
		this.tunnels = tunnels;
		this.queue = new PriorityQueue<>(20, (a, b) -> b.getPriority() - a.getPriority());
	}

	@Override
	public boolean tryToEnterInner(Vehicle vehicle){
		while (true){
			if(vehicle.toString().contains("AMBULANCE")){
				for(Tunnel tunnel: tunnels){
					if (tunnel.tryToEnterInner(vehicle)){
						map.put(vehicle, tunnel);
						BasicTunnel t = (BasicTunnel) tunnel;
						LinkedList<Vehicle> lane = t.getLane();
						for(Vehicle v: lane){
							v.wait();
						}
						return true;
					}
					if (!queue.contains(vehicle)){
						queue.add(vehicle);
					}
					try{
						wait();
					} catch (InterruptedException e){}
				}
			}else{
				//if the priority of the vehicle is not the largest
				//add it to the queue and let it wait
				if (this.queue.size() != 0){
					if (this.queue.peek().getPriority() >= vehicle.getPriority()){
						if (!queue.contains(vehicle)){
							queue.add(vehicle);
						}
						try{
							wait();
						} catch (InterruptedException e){}
					}
				}

				//add the vehicle to one of the tunnels
				for (Tunnel tunnel : tunnels){
					if (tunnel.tryToEnterInner(vehicle)){
						queue.remove(vehicle);
						map.put(vehicle, tunnel);
						return true;
					}
				}

				//if the queue is empty but no tunnel to enter
				//add the vehicle to the queue
				if (!queue.contains(vehicle)){
					queue.add(vehicle);
				}
				try{
					wait();
				} catch (InterruptedException e){}
			}
		}
	}

	@Override
	public void exitTunnelInner(Vehicle vehicle){
		map.get(vehicle).exitTunnelInner(vehicle);
		map.remove(vehicle);
		if (queue.size() != 0){
			notify();
		}
		if(vehicle.toString().contains("AMBULANCE")){
			Tunnel tunnel = map.get(vehicle);
			BasicTunnel t = (BasicTunnel) tunnel;
			LinkedList<Vehicle> lane = t.getLane();
			for(Vehicle v: lane){
				v.signal();
			}
		}
	}
}

