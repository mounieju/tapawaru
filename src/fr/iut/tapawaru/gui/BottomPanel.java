package fr.iut.tapawaru.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import fr.iut.tapawaru.team.Character;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.iut.tapawaru.action.Attack;
import fr.iut.tapawaru.action.Move;
import fr.iut.tapawaru.action.Terra;
import fr.iut.tapawaru.map.CellPosition;
import fr.iut.tapawaru.map.Map;

/**
 * Gui manager of the bottom part of the mainFrame.
 * Here we can see spells, action points, terra spells ...
 * @author jpelloux
 *
 */
public class BottomPanel extends JPanel implements KeyListener
{
/* ****************************************ATTRIBUTS******************************************** */
			/* *****************************Images******************* */
	/** Image of action point left = 3. */
	private Image apLeft3;
	
	/** Image of action point left = 2. */
	private Image apLeft2;
	
	/** Image of action point left = 1. */
	private Image apLeft1;

	/** the right side image */
	private Image right;
	
	/** The life point bar image */
	private Image hpBar;
	
			/* *****************************Map******************* */
	/** the game's Map.  */
	private Map map;
	
	/** the game's MapGUI.  */
	private MapGUI mapGui;
	
			/* *****************************Utility******************* */
	/** Say if any case is selected. */
	private boolean caseSelected;
	
	/** The character which is actually selected. */
	private Character characterSelected;
	

	/* ****************************************CONSTRUCTORS******************************************** */
	/**
	 * Constructor of BottomPanel.
	 * @param map Game's map.
	 */
	public BottomPanel(Map map)
	{
		try
		{
			this.apLeft3 = ImageIO.read(new File("img/players/apLeft3.png"));
			this.apLeft2 = ImageIO.read(new File("img/players/apLeft2.png"));
			this.apLeft1 = ImageIO.read(new File("img/players/apLeft1.png"));
			
			this.right = ImageIO.read(new File("img/botScreen/right.png"));

			this.hpBar = ImageIO.read(new File("img/botScreen/hpBar.png"));

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.map = map;
		this.caseSelected = false;
		this.mapGui = this.map.getMapGui();

	}

	/* **********************************PANNEL DISPLAY TOOLS************************* */
	/**
	 * Display the left side when a Cell is selected.
	 * @param g this.Graphics()
	 */
	public void paintTerraStateSelected(Graphics g)
	{
		Image tmp = null;
		try
		{
			tmp = ImageIO.read(new File("img/botScreen/left2" + this.map.getTeamController().getPlayingTeam().getColorTeam()
					+ "Selected.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(tmp, 0, 0, this);
		paintPA(g);
		this.caseSelected = true;

	}

	/**
	 * Display the left side when a Cell is NOT selected.
	 * @param g this.Graphics()
	 */
	public void paintTerraStateUnselected(Graphics g)
	{
		Image tmp = null;
		try
		{
			tmp = ImageIO.read(new File("img/botScreen/left2" + this.map.getTeamController().getPlayingTeam().getColorTeam() + ".png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(tmp, 0, 0, this);
		paintPA(g);
		this.caseSelected = false;
	}

	/**
	 * Paint the number of action point left.
	 * @param g this.Graphics()
	 */
	private void paintPA(Graphics g)
	{
		Image tmp = null;
		switch (this.map.getTeamController().getPlayingTeam().getActionPointsLeft())
		{
		case 1:
			tmp = this.apLeft1;
			break;
		case 2:
			tmp = this.apLeft2;
			break;
		case 3:
			tmp = this.apLeft3;
			break;
		}

		g.drawImage(tmp, 9, 130, this);
	}
	
	/**
	 * Print life point left for each Character in the right side.
	 * @param g this.Graphics()
	 */
	public void printHp(Graphics g)
	{
		for (int index = 0; index < this.map.getTeamController().getPlayingTeam().getCharacter()[0].getHealthPoint(); index++)
		{
			g.drawImage(this.hpBar, 400, 15 + 10 * index, this);
		}
		for (int index = 0; index < this.map.getTeamController().getPlayingTeam().getCharacter()[1].getHealthPoint(); index++)
		{
			g.drawImage(this.hpBar, 512, 15 + 10 * index, this);
		}
		for (int index = 0; index < this.map.getTeamController().getPlayingTeam().getCharacter()[2].getHealthPoint(); index++)
		{
			g.drawImage(this.hpBar, 640, 15 + 10 * index, this);
		}
	}
	
	/**
	 * Display method.
	 * Paint the different component of the BottomPanel.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		this.paintTerraStateUnselected(g);

		g.drawImage(this.right, 375, 0, this);

		paintPA(g);

		this.printHp(g);

	}
	
	/* ****************************************KEY LISTENER******************************************** */
	/**
	 * Key Listener for when a key is pressed d.
	 * Manage : 
	 * 			-Terra spells
	 * 			-Character's selection
	 * 			-Character's cast.
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		/* ****************************************END OF TURN******************************************** */		
		if (e.getKeyChar() == 'u')
		{
			this.map.getTeamController().skipTurn();
			this.repaint();
		}
		
		/* ****************************************TERRA******************************************** */
		if (this.caseSelected)
		{
			switch (e.getKeyChar())
			{
						/* *****************************Counter Clock Spin******************* */
			case 'w':
				Terra.glyphCCWspin(this.map, this.map.getSelectedCell().getPosition());
				this.mapGui.printGlyph(this.mapGui.getGraphics());
				this.mapGui.changeCellState(this.map.getSelectedCell().getPosition().getPositionX(), this.map.getSelectedCell().getPosition()
						.getPositionY());
				this.paint(this.getGraphics());
				break;
						/* *****************************Clock Spin******************* */
			case 'x':
				Terra.glyphCWspin(this.map, this.map.getSelectedCell().getPosition());
				this.mapGui.printGlyph(this.mapGui.getGraphics());
				this.mapGui.changeCellState(this.map.getSelectedCell().getPosition().getPositionX(), this.map.getSelectedCell().getPosition()
						.getPositionY());
				this.paint(this.getGraphics());

				break;
						/* *****************************Random******************* */
			case 'c':
				Terra.glyphRandom(this.map, this.map.getSelectedCell().getPosition());
				this.mapGui.printGlyph(this.mapGui.getGraphics());
				this.mapGui.changeCellState(this.map.getSelectedCell().getPosition().getPositionX(), this.map.getSelectedCell().getPosition()
						.getPositionY());
				this.paint(this.getGraphics());
				break;
						/* *****************************Invalid Input******************* */
			default:
			}
		}
		
		/* ****************************************Character Selection******************************************** */		
		switch (e.getKeyChar())
		{
						/* *****************************Character 1******************* */
		case 'a':
			if(!(this.map.getSelectedCell()==null))
				this.mapGui.changeCellState(this.map.getSelectedCell().getPosition().getPositionX(),this.map.getSelectedCell().getPosition().getPositionY());
			if (!this.map.getTeamController().getPlayingTeam().getCharacter()[0].isAlive())
				break;
			if (this.characterSelected == null)
			{
				this.mapGui.paintGivenCell(this.map.getTeamController().getPlayingTeam().getCharacter()[0].getCellTraveled().getPosition(),
						this.map.getTeamController().getPlayingTeam().getColorTeam() + "Selected");
				this.characterSelected = this.map.getTeamController().getPlayingTeam().getCharacter()[0];
				this.mapGui.setSelectedCharacterPosition(this.map.getTeamController().getPlayingTeam().getCharacter()[0].getCellTraveled()
						.getPosition());
				try
				{
					this.right = ImageIO.read(new File("img/botScreen/right0selected.png"));
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (this.characterSelected == this.map.getTeamController().getPlayingTeam().getCharacter()[0])
			{
				this.mapGui.paintGivenCell(this.map.getTeamController().getPlayingTeam().getCharacter()[0].getCellTraveled().getPosition(),
						this.map.getTeamController().getPlayingTeam().getColorTeam().toString());
				this.characterSelected = null;
				this.mapGui.setSelectedCharacterPosition(null);
				try
				{
					this.right = ImageIO.read(new File("img/botScreen/right.png"));
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else
			{
				this.mapGui.paintGivenCell(this.characterSelected.getCellTraveled().getPosition(), this.map.getTeamController()
						.getPlayingTeam().getColorTeam().toString());
				this.mapGui.paintGivenCell(this.map.getTeamController().getPlayingTeam().getCharacter()[0].getCellTraveled().getPosition(),
						this.map.getTeamController().getPlayingTeam().getColorTeam() + "Selected");
				this.characterSelected = this.map.getTeamController().getPlayingTeam().getCharacter()[0];
				this.mapGui.setSelectedCharacterPosition(this.map.getTeamController().getPlayingTeam().getCharacter()[0].getCellTraveled()
						.getPosition());
				try
				{
					this.right = ImageIO.read(new File("img/botScreen/right0selected.png"));
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			this.repaint();
			break;
							/* *****************************Character 2******************* */
		case 'z':
			if(!(this.map.getSelectedCell()==null))
				this.mapGui.changeCellState(this.map.getSelectedCell().getPosition().getPositionX(),this.map.getSelectedCell().getPosition().getPositionY());
			if (!this.map.getTeamController().getPlayingTeam().getCharacter()[1].isAlive())
				break;
			if (this.characterSelected == null)
			{
				this.mapGui.paintGivenCell(this.map.getTeamController().getPlayingTeam().getCharacter()[1].getCellTraveled().getPosition(),
						this.map.getTeamController().getPlayingTeam().getColorTeam() + "Selected");
				this.characterSelected = this.map.getTeamController().getPlayingTeam().getCharacter()[1];
				this.mapGui.setSelectedCharacterPosition(this.map.getTeamController().getPlayingTeam().getCharacter()[1].getCellTraveled()
						.getPosition());
				try
				{
					this.right = ImageIO.read(new File("img/botScreen/right1selected.png"));
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (this.characterSelected == this.map.getTeamController().getPlayingTeam().getCharacter()[1])
			{
				this.mapGui.paintGivenCell(this.map.getTeamController().getPlayingTeam().getCharacter()[1].getCellTraveled().getPosition(),
						this.map.getTeamController().getPlayingTeam().getColorTeam().toString());
				this.characterSelected = null;
				this.mapGui.setSelectedCharacterPosition(null);
				try
				{
					this.right = ImageIO.read(new File("img/botScreen/right.png"));
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else
			{
				this.mapGui.paintGivenCell(this.characterSelected.getCellTraveled().getPosition(), this.map.getTeamController()
						.getPlayingTeam().getColorTeam().toString());
				this.mapGui.paintGivenCell(this.map.getTeamController().getPlayingTeam().getCharacter()[1].getCellTraveled().getPosition(),
						this.map.getTeamController().getPlayingTeam().getColorTeam() + "Selected");
				this.characterSelected = this.map.getTeamController().getPlayingTeam().getCharacter()[1];
				this.mapGui.setSelectedCharacterPosition(this.map.getTeamController().getPlayingTeam().getCharacter()[1].getCellTraveled()
						.getPosition());
				try
				{
					this.right = ImageIO.read(new File("img/botScreen/right1selected.png"));
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			this.repaint();
			break;
						/* *****************************Character 3******************* */
		case 'e':
			if(!(this.map.getSelectedCell()==null))
				this.mapGui.changeCellState(this.map.getSelectedCell().getPosition().getPositionX(),this.map.getSelectedCell().getPosition().getPositionY());
			if (!this.map.getTeamController().getPlayingTeam().getCharacter()[2].isAlive())
				break;
			if (this.characterSelected == null)
			{
				this.mapGui.paintGivenCell(this.map.getTeamController().getPlayingTeam().getCharacter()[2].getCellTraveled().getPosition(),
						this.map.getTeamController().getPlayingTeam().getColorTeam() + "Selected");
				this.characterSelected = this.map.getTeamController().getPlayingTeam().getCharacter()[2];
				this.mapGui.setSelectedCharacterPosition(this.map.getTeamController().getPlayingTeam().getCharacter()[2].getCellTraveled()
						.getPosition());
				try
				{
					this.right = ImageIO.read(new File("img/botScreen/right2selected.png"));
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (this.characterSelected == this.map.getTeamController().getPlayingTeam().getCharacter()[2])
			{
				this.mapGui.paintGivenCell(this.map.getTeamController().getPlayingTeam().getCharacter()[2].getCellTraveled().getPosition(),
						this.map.getTeamController().getPlayingTeam().getColorTeam().toString());
				this.characterSelected = null;
				this.mapGui.setSelectedCharacterPosition(null);
				try
				{
					this.right = ImageIO.read(new File("img/botScreen/right.png"));
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else
			{
				this.mapGui.paintGivenCell(this.characterSelected.getCellTraveled().getPosition(), this.map.getTeamController()
						.getPlayingTeam().getColorTeam().toString());
				this.mapGui.paintGivenCell(this.map.getTeamController().getPlayingTeam().getCharacter()[2].getCellTraveled().getPosition(),
						this.map.getTeamController().getPlayingTeam().getColorTeam() + "Selected");
				this.characterSelected = this.map.getTeamController().getPlayingTeam().getCharacter()[2];
				this.mapGui.setSelectedCharacterPosition(this.map.getTeamController().getPlayingTeam().getCharacter()[2].getCellTraveled()
						.getPosition());
				try
				{
					this.right = ImageIO.read(new File("img/botScreen/right2selected.png"));
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			this.repaint();
			break;
		default:
		}
		
		/* ****************************************SPELLS******************************************** */
		if (!(this.characterSelected == null))
		{
						/* *****************************Laser Beam******************* */
			switch (e.getKeyChar())
			{
			case 'q':
				if (this.map.getSelectedCell() == null)
				{
					// TODO You must select before cast a spell
				} else
				{
					Attack.laserBeam(this.map, this.characterSelected, this.map.getSelectedCell().getPosition(), true);
					this.repaint();
					this.mapGui.printCell(this.mapGui.getGraphics());
					this.characterSelected = null;
					this.mapGui.setSelectedCharacterPosition(null);
					this.map.setSelectedCell(null);
					this.caseSelected = false;
					try
					{
						this.right = ImageIO.read(new File("img/botScreen/right.png"));
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
						/* *****************************Around Caster******************* */
			case 's':
					Attack.aroundCaster(this.map, this.characterSelected, true);
					this.repaint();
					this.mapGui.printCell(this.mapGui.getGraphics());
					this.characterSelected = null;
					this.mapGui.setSelectedCharacterPosition(null);
					this.map.setSelectedCell(null);
					this.caseSelected = false;
					try
					{
						this.right = ImageIO.read(new File("img/botScreen/right.png"));
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();		
				}
				break;
						/* *****************************Flower Bomb******************* */
			case 'd':
				if (this.map.getSelectedCell() == null)
				{
					// TODO You must select before cast a spell
				} else
				{
					Attack.flowerBomb(this.map, this.characterSelected, this.map.getSelectedCell().getPosition(), true);
					this.repaint();
					this.mapGui.printCell(this.mapGui.getGraphics());
					this.characterSelected = null;
					this.mapGui.setSelectedCharacterPosition(null);
					this.map.setSelectedCell(null);
					this.caseSelected = false;
					try
					{
						this.right = ImageIO.read(new File("img/botScreen/right.png"));
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
				break;
					/* *****************************Move******************* */
			case ' ':
				if (this.map.getSelectedCell() == null)
				{
					Image tmp = null;
					try
					{
						tmp = ImageIO.read(new File("img/octoSelectable.png"));
					} catch (IOException k)
					{
						// TODO Auto-generated catch block
						k.printStackTrace();
					}
					for (CellPosition cellpos : Move.getAccessiblePos(this.map, this.characterSelected))
					{

						this.mapGui.paintGivenCell(cellpos, tmp);
					}

				} else
				{
					Move.simpleMove(this.map, this.characterSelected, this.map.getSelectedCell().getPosition());
					this.repaint();
					this.mapGui.printCell(this.mapGui.getGraphics());
					this.characterSelected = null;
					try
					{
						this.right = ImageIO.read(new File("img/botScreen/right.png"));
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					this.mapGui.setSelectedCharacterPosition(null);
					this.map.setSelectedCell(null);
					this.caseSelected = false;
				}
				break;
			default:
			}
		}
	}

	/**
	 * Empty.
	 * Key Listener for when a key is released.
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Empty.
	 * Key Listener for when a key is pressed then released.
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	
}
