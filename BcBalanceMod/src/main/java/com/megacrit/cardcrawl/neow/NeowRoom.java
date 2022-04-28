package com.megacrit.cardcrawl.neow;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class NeowRoom extends AbstractRoom {
    public NeowRoom(boolean isDone) {
        this.phase = AbstractRoom.RoomPhase.EVENT;
        this.event = new BcNeowEvent(isDone);
        this.event.onEnterRoom();
    }
    
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }
    
    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp) {
            this.event.update();
        }
        
        if (this.event.waitTimer == 0.0F && !this.event.hasFocus && this.phase != AbstractRoom.RoomPhase.COMBAT) {
            this.phase = AbstractRoom.RoomPhase.COMPLETE;
            this.event.reopen();
        }
        
    }
    
    public void render(SpriteBatch sb) {
        super.render(sb);
        this.event.render(sb);
    }
    
    public void renderAboveTopPanel(SpriteBatch sb) {
        super.renderAboveTopPanel(sb);
        if (this.event != null) {
            this.event.renderAboveTopPanel(sb);
        }
        
    }
}
