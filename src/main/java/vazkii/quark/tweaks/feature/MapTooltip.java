package vazkii.quark.tweaks.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.quark.base.module.Feature;

public class MapTooltip extends Feature {
	
    private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderTooltip(RenderTooltipEvent.PostText event) {
		if(event.getStack() != null && event.getStack().getItem() instanceof ItemMap) {
			Minecraft mc = Minecraft.getMinecraft();
			GlStateManager.pushMatrix();
	        GlStateManager.disableLighting();
	        mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
	        Tessellator tessellator = Tessellator.getInstance();
	        VertexBuffer vertexbuffer = tessellator.getBuffer();

	        int pad = 7;
	        float size = 135;
	        float scale = 0.5F;
	        
	        GlStateManager.translate(event.getX(), event.getY() - size * scale - 5, 0);
	        GlStateManager.scale(scale, scale, scale);
	        
	        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
	        vertexbuffer.pos(-pad, size, 0.0D).tex(0.0D, 1.0D).endVertex();
	        vertexbuffer.pos(size, size, 0.0D).tex(1.0D, 1.0D).endVertex();
	        vertexbuffer.pos(size, -pad, 0.0D).tex(1.0D, 0.0D).endVertex();
	        vertexbuffer.pos(-pad, -pad, 0.0D).tex(0.0D, 0.0D).endVertex();
	        tessellator.draw();
	        
	        MapData mapdata = Items.FILLED_MAP.getMapData(event.getStack(), mc.world);

	        if(mapdata != null)
	            mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);

	        GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}
	
	@Override
	public boolean hasSubscriptions() {
		return isClient();
	}
	
}
