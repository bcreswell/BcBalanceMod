//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.NemesisFireParticle;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;

public class Nemesis extends AbstractMonster
{
    public static final String ID = "Nemesis";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    private static final int HP = 185;
    private static final int A_2_HP = 200;
    private static final int SCYTHE_COOLDOWN_TURNS = 2;
    private static final float HB_X = 5.0F;
    private static final float HB_Y = -10.0F;
    private static final int SCYTHE_DMG = 45;
    private static final int FIRE_DMG = 6;
    private static final int FIRE_TIMES = 3;
    private static final int A_2_FIRE_DMG = 7;
    private static final int BURN_AMT = 3;
    private int fireDmg;
    private  int bigAttackDmg;
    private int scytheCooldown = 0;
    private static final byte TRI_ATTACK = 2;
    private static final byte SCYTHE = 3;
    private static final byte TRI_BURN = 4;
    private float fireTimer = 0.0F;
    private static final float FIRE_TIME = 0.05F;
    private Bone eye1;
    private Bone eye2;
    private Bone eye3;
    private boolean firstMove = true;
    
    public Nemesis()
    {
        super(NAME, "Nemesis", 185, 5.0F, -10.0F, 350.0F, 440.0F, (String) null, 0.0F, 0.0F);
        type = EnemyType.ELITE;
        loadAnimation("images/monsters/theForest/nemesis/skeleton.atlas", "images/monsters/theForest/nemesis/skeleton.json", 1.0F);
        TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        eye1 = skeleton.findBone("eye0");
        eye2 = skeleton.findBone("eye1");
        eye3 = skeleton.findBone("eye2");
        if (AbstractDungeon.ascensionLevel >= 8)
        {
            //setHp(200);
            setHp(240);
        }
        else
        {
            setHp(185);
        }
    
        if (AbstractDungeon.ascensionLevel >= 18)
        {
            bigAttackDmg = 45;
            fireDmg = 9;
        }
        else if (AbstractDungeon.ascensionLevel >= 3)
        {
            bigAttackDmg = 45;
            fireDmg = 7;
        }
        else
        {
            bigAttackDmg = 35;
            fireDmg = 6;
        }
        
        damage.add(new DamageInfo(this, bigAttackDmg));
        damage.add(new DamageInfo(this, fireDmg));
    }
    
    public void takeTurn()
    {
label24:
        switch (nextMove)
        {
            case 2:
                int i = 0;
                
                while (true)
                {
                    if (i >= 3)
                    {
                        break label24;
                    }
                    
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(1), AttackEffect.FIRE));
                    ++i;
                }
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AttackEffect.SLASH_HEAVY));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_NEMESIS_1C"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(hb.cX, hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveType.CHAOTIC), 1.5F));
                if (AbstractDungeon.ascensionLevel >= 18)
                {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Burn(), 5));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Burn(), 3));
                }
        }
        
        if (!hasPower("Intangible"))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePower(this, 1)));
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    public void damage(DamageInfo info)
    {
        if (info.output > 0 && hasPower("Intangible"))
        {
            info.output = 1;
        }
        
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0)
        {
            TrackEntry e = state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.8F);
        }
        
        super.damage(info);
    }
    
    public void changeState(String key)
    {
        byte var3 = -1;
        switch (key.hashCode())
        {
            case 1941037640:
                if (key.equals("ATTACK"))
                {
                    var3 = 0;
                }
            default:
                switch (var3)
                {
                    case 0:
                        TrackEntry e = state.setAnimation(0, "Attack", false);
                        state.addAnimation(0, "Idle", true, 0.0F);
                        e.setTimeScale(0.8F);
                    default:
                }
        }
    }
    
    protected void getMove(int num)
    {
        --scytheCooldown;
        if (firstMove)
        {
            firstMove = false;
            if (num < 50)
            {
                setMove((byte) 2, Intent.ATTACK, fireDmg, 3, true);
            }
            else
            {
                setMove((byte) 4, Intent.DEBUFF);
            }
            
        }
        else
        {
            if (num < 30)
            {
                if (!lastMove((byte) 3) && scytheCooldown <= 0)
                {
                    setMove((byte) 3, Intent.ATTACK, bigAttackDmg);
                    scytheCooldown = 2;
                }
                else if (AbstractDungeon.aiRng.randomBoolean())
                {
                    if (!lastTwoMoves((byte) 2))
                    {
                        setMove((byte) 2, Intent.ATTACK, fireDmg, 3, true);
                    }
                    else
                    {
                        setMove((byte) 4, Intent.DEBUFF);
                    }
                }
                else if (!lastMove((byte) 4))
                {
                    setMove((byte) 4, Intent.DEBUFF);
                }
                else
                {
                    setMove((byte) 2, Intent.ATTACK, fireDmg, 3, true);
                }
            }
            else if (num < 65)
            {
                if (!lastTwoMoves((byte) 2))
                {
                    setMove((byte) 2, Intent.ATTACK, fireDmg, 3, true);
                }
                else if (AbstractDungeon.aiRng.randomBoolean())
                {
                    if (scytheCooldown > 0)
                    {
                        setMove((byte) 4, Intent.DEBUFF);
                    }
                    else
                    {
                        setMove((byte) 3, Intent.ATTACK, bigAttackDmg);
                        scytheCooldown = 2;
                    }
                }
                else
                {
                    setMove((byte) 4, Intent.DEBUFF);
                }
            }
            else if (!lastMove((byte) 4))
            {
                setMove((byte) 4, Intent.DEBUFF);
            }
            else if (AbstractDungeon.aiRng.randomBoolean() && scytheCooldown <= 0)
            {
                setMove((byte) 3, Intent.ATTACK, bigAttackDmg);
                scytheCooldown = 2;
            }
            else
            {
                setMove((byte) 2, Intent.ATTACK, fireDmg, 3, true);
            }
            
        }
    }
    
    private void playSfx()
    {
        int roll = MathUtils.random(1);
        if (roll == 0)
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_NEMESIS_1A"));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_NEMESIS_1B"));
        }
        
    }
    
    private void playDeathSfx()
    {
        int roll = MathUtils.random(1);
        if (roll == 0)
        {
            CardCrawlGame.sound.play("VO_NEMESIS_2A");
        }
        else
        {
            CardCrawlGame.sound.play("VO_NEMESIS_2B");
        }
        
    }
    
    public void die()
    {
        playDeathSfx();
        super.die();
    }
    
    public void update()
    {
        super.update();
        if (!isDying)
        {
            fireTimer -= Gdx.graphics.getDeltaTime();
            if (fireTimer < 0.0F)
            {
                fireTimer = 0.05F;
                AbstractDungeon.effectList.add(new NemesisFireParticle(skeleton.getX() + eye1.getWorldX(), skeleton.getY() + eye1.getWorldY()));
                AbstractDungeon.effectList.add(new NemesisFireParticle(skeleton.getX() + eye2.getWorldX(), skeleton.getY() + eye2.getWorldY()));
                AbstractDungeon.effectList.add(new NemesisFireParticle(skeleton.getX() + eye3.getWorldX(), skeleton.getY() + eye3.getWorldY()));
            }
        }
        
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Nemesis");
        NAME = monsterStrings.NAME;
    }
}
