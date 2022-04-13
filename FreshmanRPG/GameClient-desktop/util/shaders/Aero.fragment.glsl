// Fragment shader based on LWJGL Shaders Lesson 4 by Mattdesl
//  https://github.com/mattdesl/lwjgl-basics/wiki/ShaderLesson4
// Linear blur based on http://www.gamerendering.com/2008/10/11/gaussian-blur-filter-shader/

//"in" attributes from our vertex shader
varying vec4 v_color;
varying vec2 v_texCoords;

//our different texture units
uniform sampler2D u_texture; //default GL_TEXTURE0, expected by SpriteBatch
uniform sampler2D u_mask;

const float blurSize = 1.0/512.0;
 

void main(void) {
    //sample the colour from the first texture
    vec4 texColor0 = texture2D(u_texture, v_texCoords);

    //get the mask; we will only use the alpha channel
    // the alpha value determines the strength of the blur
    float mask = texture2D(u_mask, v_texCoords).a;

	vec4 blur = texColor0;
	//ignore nearly transparent and opaque regions for blurring
	if (mask < 0.95 && mask > 0.10)
	{
		// perform a linear decay blur
		// take nine samples, with the distance blurSize between them
		blur = vec4(0.0);
 
		float amount;
		float percentage;
		for (amount = 4.0, percentage = 0.05; amount >= 1.0; percentage += amount / 100.0, amount -= 1.0)
		{
			float effect = (amount * mask) * blurSize;
			blur += texture2D(u_texture, vec2(v_texCoords.x - effect, v_texCoords.y - effect)) * percentage;
	   		blur += texture2D(u_texture, vec2(v_texCoords.x + effect, v_texCoords.y + effect)) * percentage;
	   	}
	   	blur += texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y)) * percentage;
	}
	
    //take the sample blurred base texture and place the other textures directly on top
    gl_FragColor = blur;
}