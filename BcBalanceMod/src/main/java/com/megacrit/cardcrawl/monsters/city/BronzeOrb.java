package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class BronzeOrb extends AbstractMonster
{
    public static final String ID = "BronzeOrb";
    private static final MonsterStrings monsterStrings;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 52;
    private static final int HP_MAX = 58;
    private static final int A_2_HP_MIN = 54;
    private static final int A_2_HP_MAX = 60;
    private static final int BEAM_DMG = 8;
    private static final int BLOCK_AMT = 12;
    private static final byte BEAM = 1;
    private static final byte SUPPORT_BEAM = 2;
    private static final byte STASIS = 3;
    private boolean usedStasis = false;
    private int count;
    
    public BronzeOrb(float x, float y, int count)
    {
        super(monsterStrings.NAME, "BronzeOrb", AbstractDungeon.monsterHpRng.random(52, 58), 0.0F, 0.0F, 160.0F, 160.0F, "images/monsters/theCity/automaton/orb.png", x, y);
        if (AbstractDungeon.ascensionLevel >= 9)
        {
            setHp(54, 60);
        }
        else
        {
            setHp(52, 58);
        }
        
        this.count = count;
        damage.add(new DamageInfo(this, 8));
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case BEAM:
                addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
                addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
                addToBot(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, hb.cX, hb.cY), 0.3F));
                addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.NONE));
                break;
            case SUPPORT_BEAM:
                addToBot(new GainBlockAction(AbstractDungeon.getMonsters().getMonster("BronzeAutomaton"), this, 12));
                break;
            case STASIS:
                addToBot(new ApplyStasisAction(this));
        }
        
        addToBot(new RollMoveAction(this));
    }
    
    public void update()
    {
        super.update();
        if (count % 2 == 0)
        {
            animY = MathUtils.cosDeg((float) (System.currentTimeMillis() / 6L % 360L)) * 6.0F * Settings.scale;
        }
        else
        {
            animY = -MathUtils.cosDeg((float) (System.currentTimeMillis() / 6L % 360L)) * 6.0F * Settings.scale;
        }
        
    }
    
    protected void getMove(int num)
    {
        if (!usedStasis && num >= 25)
        {
            setMove(STASIS, AbstractMonster.Intent.STRONG_DEBUFF);
            usedStasis = true;
        }
        else if (num >= 70 && !lastTwoMoves(SUPPORT_BEAM))
        {
            setMove(SUPPORT_BEAM, AbstractMonster.Intent.DEFEND);
        }
        else if (!lastTwoMoves(BEAM))
        {
            setMove(BEAM, AbstractMonster.Intent.ATTACK, 8);
        }
        else
        {
            setMove(SUPPORT_BEAM, AbstractMonster.Intent.DEFEND);
        }
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BronzeOrb");
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
