package com.tentone.constellations.elements;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.tentone.constellations.Player;

public class World extends Rectangle
{
	private static final long serialVersionUID = 2597058350349965364L;
	
	//Planets and creatures
	public ConcurrentLinkedQueue<Planet> planets;
	public ConcurrentLinkedQueue<Creature> creatures;
	
	//World time
	public double time;
	
	//World constructor
	public World()
	{
		super(0, 0, 45, 25);
		
		this.planets = new ConcurrentLinkedQueue<Planet>();
		this.creatures = new ConcurrentLinkedQueue<Creature>();
		
		this.time = 0.0;
	}
	
	//Generate random world
	public static World generateWorld()
	{
		World world = new World();
		
		Player a = new Player("a");
		Player b = new Player("b");
		
		//Create planets
		for(int i = 0; i < 10; i++)
		{
			boolean colliding = true;
			Planet planet = null;
			
			while(colliding)
			{
				colliding = false;
				
				planet = new Planet((int)Math.ceil(Math.random() * 2.0) + 1);
				if(i < 2)
				{
					planet.setOwner((i == 0) ? a : (i == 1) ? b : null);
					planet.setLevel(1);
				}
				
				
				while(!world.contains(planet))
				{
					planet.setPosition((float)Math.random() * world.width, (float)Math.random() * world.height);
				}
				
				Iterator<Planet> itp = world.planets.iterator();
				while(itp.hasNext())
				{
					if(planet.overlaps(itp.next()))
					{
						colliding = true;
					}
				}
			}
			
			world.addPlanet(planet);
		}
		
		//Create creatures at random
		/*for(int i = 0; i < 1000; i++)
		{
			Creature creature = new Creature();
			creature.setPosition((float)Math.random() * world.width, (float)Math.random() * world.height);
			creature.owner = (i < 500) ? a : b;
			
			world.addCreature(creature);
		}*/
		
		return world;
	}
	
	//Update world state
	public void update(float delta)
	{
		//Don't let delta get to high
		if(delta > 0.04f)
		{
			delta = 0.04f;
		}
		
		//Update world time
		this.time += delta;
		
		//Update creatures state
		Iterator<Creature> creatures = this.creatures.iterator();
		while(creatures.hasNext())
		{
			creatures.next().update(delta);
		}
		
		//Update planets
		Iterator<Planet> planets = this.planets.iterator();
		while(planets.hasNext())
		{
			planets.next().update(delta);
		}
	}
	
	//Add a planet to the world
	public void addPlanet(Planet planet)
	{
		planet.world = this;
		this.planets.add(planet);
	}
	
	//Add a creature to the world
	public void addCreature(Creature creature)
	{
		creature.world = this;
		this.creatures.add(creature);
	}
	
	//Check if a planet is inside the world
	public boolean contains(Circle circle)
	{
		float xmin = circle.x - circle.radius;
		float xmax = circle.x + circle.radius;

		float ymin = circle.y - circle.radius;
		float ymax = circle.y + circle.radius;
		
		return (xmin >= x && xmin <= x + width) && (xmax >= x && xmax <= x + width)
			&& (ymin >= y && ymin <= y + height) && (ymax >= y && ymax <= y + height);
	}
}
