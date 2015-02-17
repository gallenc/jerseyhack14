<?php
/**
 * The template for displaying licence info.
 * uses httpful.phar library
 * 
 * Usecase
 * 
 * Data used by page
 * 1. metadata edd_osgiLicenceStr contains licence string calculatedc from liceme metadataspec
 * 2. metadata osgiLicenceMetadata contains spec and/or completed licence metadata
 *        edd_osgiLicenceMetaDataSpec XML representation of the specification for licence
 *        edd_osgiLicenceMetadataStr XML represenation of the actual metadata used to construct licence
 *          (this is constructed from the licenceMetaDataSpec, user input and user account data).
 * 4. metadata edd_osgiLicencee username of customer who can view and modify this licence
 * 5. metadata osgiProductId maven unique id of proruct to which this licence applies
 * 
 * Processing Steps
 * [on load page]
 * check customer can view/modify licence - return error in not
 * check productId set. If not return an error
 * if licenceStr populated then just display page with all values input disabled 
 *    (check licenceMetadata is also populated else return an error) 
 * check you can communicate with OSGi licence manager if not return an error
 * if licenceMetadata populated then display metadata for editing and / or generating licence 
 * 
 * on button [Generate licence]
 * check required fields are populated in licenceMetadata (all fields). 
 * Return to user if not correct 
 * else generate licence 
 * if licence generator returns an error reflect to user (fields may not be correct)
 * 
 * Overall processing steps
 * 1. display product details - allow user to select product for purchase
 * 2. purchase product - pre credit card check load licenceMetadataSpec for productid
 *       edd_osgiLicencee username of customer who can view and modify this licence
 *       edd_osgiProductId maven unique id of purchased product to which this licence applies
 *       retrieve  edd_osgiLicenceMetadataSpec for productId from Karaf licence manager.
 *       populate  edd_osgiLicenceMetadataSpec with edd_osgiLicencee data
 *    [if not fail and do not process transaction]
 * 3. post purchase create new licence post/page with 
 *       publish page and redirect user to page
 * 4. user views page and generates licence at will
 *   (each purhased licence references PO and PO references licence page)
 *   page has [create licence] button and possibly ( property defined ) reset button to regenerate
 * 
 * metadata used
 * edd_osgiProductIdStr        contains maven unique id of product to which this licence applies
 * edd_osgiLicenceStr          contains licence string calculatedc from liceme metadataspec
 * edd_osgiLicenceMetadataStr  contains partly complete or complete licence metadata with user input to complete licence metadata 
 * edd_osgiLicencee            contains username of customer who can view and modify and owns this licence
 * edd_osgiLicenceMetadataSpecStr  contains specification for the licence metadata
 * 
 * 
 * THIS IS LICENCE PAGE TEMPLATE for creating a licence
 */
// load the header for this plugin
get_header ();

// check if OSGi licence generator debugging is enabled
//$osgipub_osgi_debug = get_option ( 'osgipub_osgi_debug' );
//TODO MAKE A VARIABLE IN START
$osgipub_osgi_debug = 'on';
if ($osgipub_osgi_debug == 'on') {
	?>
<h2>OSGi Licence Publisher debugging enabled</h2>
<textarea name="osgipluggindebug" rows="10" cols="50" noedit>
<?php
	echo 'OSGi Licence Publisher debugging start';
}
?>
<?php

/**
 * Constructs a table of inputs for licenceMetadata
 *
 * @param SimpleXMLElement $_licenceMetadata        	
 * @param SimpleXMLElement $_licenceMetadataSpec        	
 * @param boolean $noinput
 *        	if true all inputs are readonly
 * @return string
 */
function licenceMetadataForm($_licenceMetadata, $_licenceMetadataSpec, $noinput) {
	$MetadataFormStr = "";
	$MetadataFormStr .= "<table id=\"edd-osgi-licenceMetadata\" style=\"width: 100%;border: 3px solid;\" >\n";
	$metadataspec = ( array ) $_licenceMetadataSpec->children ();
	foreach ( $_licenceMetadata->children () as $key => $value ) {
		if ($key != "options") {
			$MetadataFormStr .= "    <tr>\n";
			if (('' !== ( string ) $metadataspec [$key]) || $noinput) {
				$MetadataFormStr .= "    <td>" . $key . "</td>\n";
			} else {
				$MetadataFormStr .= "    <td>" . $key . " <bold>**</bold></td>\n"; // ** indicates editable field
			}
			$MetadataFormStr .= "        <td>\n";
			$MetadataFormStr .= "            <input type=\"text\" name=\"" . $key . "\" value=\"" . $value . "\" ";
			if ('' !== ( string ) $metadataspec [$key] || $noinput) {
				$MetadataFormStr .= " readonly";
			}
			$MetadataFormStr .= ">\n";
			$MetadataFormStr .= "        </td>\n";
			$MetadataFormStr .= "    </tr>\n";
		}
	}
	// TODO options
	//$specOptions = ( array ) $licenceMetadataSpec->options->children ();
// 	echo "debug XXXXvar_dump=" . var_dump($specOptions );
// 	foreach ( $_licenceMetadata->options->children () as $option ) {
// 		$name = $option->name;
// 		$value = $option->value;
// 		$description = $option->description;
// 		$MetadataFormStr .= "    <tr>\n";
// 		if (('' != $specOptions [$key]) || $noinput) {
// 			$MetadataFormStr .= "      <td>" . $name . "</td>\n";
// 		} else {
// 			$MetadataFormStr .= "      <td>" . $name . " <bold>**</bold></td>\n"; // ** indicates editable field
// 		}
// 		$MetadataFormStr .= "      <td>\n";
// 		$MetadataFormStr .= "            <input type=\"text\" name=\"" . $name . "\" value=\"" . $value . "\"";
// 		if ('' != $specOptions [$key] || $noinput) {
// 			$MetadataFormStr .= " readonly";
// 		}
// 		$MetadataFormStr .= ">\n";
// 		$MetadataFormStr .= "        </td>\n";
// 		$MetadataFormStr .= "        <td>" . $description . "</td>\n";
// 		$MetadataFormStr .= "    </tr>\n";
// 	}
	$MetadataFormStr .= "</table>\n";
	return $MetadataFormStr;
}
/**
 *
 * @param SimpleXMLElement $_licenceMetadata        	
 * @param string $osgiLicenceGeneratorUrl        	
 * @param string $osgi_username        	
 * @param string $osgi_password        	
 * @param string $osgipub_osgi_debug        	
 * @param int $post_id        	
 * @return string licence string
 */
function generateLicence($_licenceMetadataSpec, $_licenceMetadata, $osgiLicenceGeneratorUrl, $osgi_username, $osgi_password, $osgipub_osgi_debug, $post_id) {
	$metadataspec = ( array ) $_licenceMetadataSpec->children ();
	$metadata = ( array ) $_licenceMetadata->children ();
	// populate fields of licenceMetadata with data from the form
	foreach ( $_licenceMetadata->children () as $key => $value ) {
		if ($key != "options") {
			// this prevents accepting post of fields already populated in licence metadata spec
			if (isset ( $_POST [$key] )) {
				if ('' == ( string ) $metadataspec [$key]) {
					// this is complex because xpath appears to be only way to set value in SimpleXMLElement
					$keyNodes = $_licenceMetadata->xpath ( '//' . $key );
					$keyNode = $keyNodes [0];
					$keyNode->{0} = htmlspecialchars ( $_POST [$key] );
				}
			}
		}
	}
	//TODO options
// 	$specOptions = ( array ) $_licenceMetadataSpec->options;
// 	$options = ( array ) $_licenceMetadata->options;
// 	foreach ( $_licenceMetadata->options->children () as $key => $value ) {
// 		// this prevents accepting post of fields already populated in licence metadata spec
// 		if (isset ( $_POST [$key] )) {
// 			if ('' == ( string ) $specOptions [$key]) {
// 				$keyNodes = $_licenceMetadata->options->xpath ( '//' . $key );
// 				$keyNode = $keyNodes [0];
// 				$keyNode->{0} = htmlspecialchars ( $_POST [$key] );
// 			}
// 		}
// 	}
	
	$uri = $osgiLicenceGeneratorUrl . '/pluginmgr/rest/licence-pub/createlicence';
	
	$payload = ( string ) $_licenceMetadata->asXML ();
	// save updated licence metadata
	update_post_meta ( $post_id, 'edd_osgiLicenceMetadataStr', $payload );
	
	if ($osgipub_osgi_debug == 'on')
		echo "Post request to licence publisher: Basic Authentication username='" . $osgi_username . "' password='" . $osgi_password . "'\n     uri='" . $uri . "'\n" . "     payload='" . $payload . "\n";
	
	$response = \Httpful\Request::post ( $uri, $payload, 'application/xml' )->authenticateWith ( $osgi_username, $osgi_password )->expectsXml ()->send ();
	
	if ($osgipub_osgi_debug == 'on')
		echo "response code='" . $response->code . "' reply payload='" . var_dump ( $response->body ) . "\n";
		
		// if we cant talk to the licence generator error and leave page
	if ($response->code != 200) {
		$msg = 'null';
		$devmsg = 'null';
		$code = $response->code;
		if (isset ( $response->errorMessage )) {
			$devmsg = ( string ) $response->errorMessage->developerMessage;
			$msg = ( string ) $response->errorMessage->message;
		}
		throw new Exception ( "edd-osgi: http error code='" . $code . "\n" . "     Cannot generate licence from url=' . $uri . '\n" . "     Reason=' . $msg . '\n" . "     Developer Message='" . $devmsg . "'\n" );
	}
	
	$licenceStr = $response->body->licence;
	
	return ( string ) $licenceStr;
}

// start of page response
try {
	// get the local post id
	$post_id = get_the_ID ();
	
	// debug data
	// update_post_meta ( $post_id, 'edd_osgiLicenceMetadataSpecStr', '' ); //TODO DEBUG REMOVE
	// update_post_meta ( $post_id, 'edd_osgiLicenceMetadataStr', '' ); //TODO DEBUG REMOVE
	// see http://www.smashingmagazine.com/2011/03/08/ten-things-every-wordpress-plugin-developer-should-know/
	// contains maven unique id of product to which this licence applies
	// TODO REMOVE - THIS IS ONLY FOR TEST
	//update_post_meta ( $post_id, 'edd_osgiProductIdStr', 'org.opennms.co.uk/org.opennms.co.uk.newfeature/0.0.1-SNAPSHOT' );
	// end debug data
	
	// get the plugin settings and throw an error if setting not set
	
	// get the base url URL of the OSGi licence generator service from settings
	$osgiLicenceGeneratorUrl = get_option ( 'osgipub_osgi_licence_pub_url' );
	if (! isset ( $osgiLicenceGeneratorUrl ) || '' == $osgiLicenceGeneratorUrl) {
		throw new Exception ( 'edd-osgi: You must set the OSGi Licence Publisher URL in the plugin settings' );
	}
	
	// get the username to access the OSGi licence generator service from settings
	$osgi_username = get_option ( 'osgipub_osgi_username' );
	if (! isset ( $osgi_username )) {
		throw new Exception ( 'edd-osgi: You must set the OSGi User Name in the plugin settings' );
	}
	
	// get the password to access of the OSGi licence generator service from settings
	$osgi_password = get_option ( 'osgipub_osgi_password' );
	if (! isset ( $osgi_password )) {
		throw new Exception ( 'edd-osgi: You must set the OSGi Password in the plugin settings' );
	}
	
	// reset the edd_osgiLicenceStr and edd_osgiLicenceMetadataStr to empty if 'reset licence' is called
	// TODO check if user can do a reset
	if (isset ( $_POST ['resetLicence'] ) && 'true' == $_POST ['resetLicence']) {
		if ($osgipub_osgi_debug == 'on')
			echo "Reset licence button pressed. Licence has been reset.\n";
		update_post_meta ( $post_id, 'edd_osgiLicenceStr', '' );
	}
	
	if (isset ( $_POST ['resetLicenceSpec'] ) && 'true' == $_POST ['resetLicenceSpec']) {
		if ($osgipub_osgi_debug == 'on')
			echo "Reset Licence Spec button pressed. Licence, Licence metadata and Licence metadata spec has been reset.\n";
		update_post_meta ( $post_id, 'edd_osgiLicenceStr', '' );
		update_post_meta ( $post_id, 'edd_osgiLicenceMetadataStr', '' );
		update_post_meta ( $post_id, 'edd_osgiLicenceMetadataSpecStr', '' );
	}
	
	// contains maven unique id of product to which this licence applies
	$edd_osgiProductIdStr = get_post_meta ( $post_id, 'edd_osgiProductIdStr', true );
	// contains licence string calculatedc from liceme metadataspec
	$edd_osgiLicenceStr = get_post_meta ( $post_id, 'edd_osgiLicenceStr', true );
	// contains spec and/or completed licence metadata
	$edd_osgiLicenceMetadataStr = get_post_meta ( $post_id, 'edd_osgiLicenceMetadataStr', true );
	// contains XML representation of the specification for licence
	$edd_osgiLicenceMetadataSpecStr = get_post_meta ( $post_id, 'edd_osgiLicenceMetadataSpecStr', true );
	
	// THESE ARE POPULATED BY EDD
	// contains username of customer who can view and modify this licence
	$edd_osgiLicencee = get_post_meta ( $post_id, 'edd_osgiLicencee', true );
	// post id of the associated payment (used to cross link back to payment)
	$edd_payment_post_id = (int) get_post_meta ( $post_id, 'edd_payment_post_id', true );
	
	// if a licence has already been constructed do not allow it to be changed
	// if a licence is not populated then allow editing of Metadata fields and generation of licence
	if (! isset ( $edd_osgiLicenceStr ) || ($edd_osgiLicenceStr == '') ) {
		$noEditMetadata = FALSE;
	} else {
		$noEditMetadata = TRUE;
	}
	
	$uri = $osgiLicenceGeneratorUrl . '/pluginmgr/rest/licence-pub/getlicencemetadataspec?productId=' . $edd_osgiProductIdStr;
	
	if ($osgipub_osgi_debug == 'on')
		echo "Get Licence Metadata Spec request to licence publisher: Basic Authentication\n" . "     username='" . $osgi_username . "' password='" . $osgi_password . "'\n" . "     uri='" . $uri . "\n";
	
	$response = \Httpful\Request::get ( $uri )->authenticateWith ( $osgi_username, $osgi_password )->expectsXml ()->send ();
	
	if ($osgipub_osgi_debug == 'on') {
		// var_dump($response);
		echo "\nResponse from licence publisher: Http response code='" . $response->code . "' response body='" . $response->body->asXML () . "'\n";
	}
	
	// if we cant talk to the licence generator error and leave page
	if ($response->code != 200) {
		$msg = 'null';
		$devmsg = 'null';
		$code = $response->code;
		if (isset ( $response->errorMessage )) {
			$devmsg = ( string ) $response->errorMessage->developerMessage;
			$msg = ( string ) $response->errorMessage->message;
		}
		throw new Exception ( "edd-osgi: Http error code='" . $code . "\n" . "     Cannot retrieve licence spec from OSGi licence publisher url=' . $uri . '\n" . "     Reason=' . $msg . '\n" . "     Developer Message='" . $devmsg . "'\n" );
	}
	
	// the first time it is viewed, we populate licence page with new licence metadata specification
	// there after we use the licence metadata specification which was first populated
	if (! isset ( $edd_osgiLicenceMetadataSpecStr ) || $edd_osgiLicenceMetadataSpecStr == NULL || $edd_osgiLicenceMetadataSpecStr == '') {
		if ($osgipub_osgi_debug == 'on')
			echo "the first time page is viewed, we populate edd_osgiLicenceMetadataSpecStr property  with response->body->licenceMetadata->asXML\n";
		$edd_osgiLicenceMetadataSpecStr = $response->body->licenceMetadataSpec->asXML ();
		update_post_meta ( $post_id, 'edd_osgiLicenceMetadataSpecStr', $edd_osgiLicenceMetadataSpecStr );
	} elseif ($osgipub_osgi_debug == 'on')
		echo "not first time page has been viewed so edd_osgiLicenceMetadataSpecStr is already populated\n";
		
		// parse the licence matadata specification we have just saved as a string
	$osgilicenceMetadataSpec = new SimpleXMLElement ( $edd_osgiLicenceMetadataSpecStr );
	
	// the first time page is viewed, we populate licence metadata with licence metadata specification
	// and with local user information in order to create a licence
	// TODO include user / cutomer metadata in the licence metadata
	if (! isset ( $edd_osgiLicenceMetadataStr ) || $edd_osgiLicenceMetadataStr == NULL || $edd_osgiLicenceMetadataStr == '') {
		if ($osgipub_osgi_debug == 'on')
			echo "the first time page is viewed, we populate osgiLicenceMetadata property  with osgiLicenceMetaDataSpec\n";
			// simple string replace to turn <licenceMetadataSpec>...</licenceMetadataSpec> into <licenceMetadata>...</licenceMetadata>
		$edd_osgiLicenceMetadataStr = str_replace ( "licenceMetadataSpec", "licenceMetadata", $edd_osgiLicenceMetadataSpecStr );
		
		// add user information for licencee
		$osgilicenceMetadata = new SimpleXMLElement ( $edd_osgiLicenceMetadataStr );
		// this is complex because xpath appears to be only way to set value in SimpleXMLElement
		$keyNodes = $osgilicenceMetadata->xpath ( '//licensee' );
		$keyNode = $keyNodes [0];
		$keyNode->{0} = htmlspecialchars ( $edd_osgiLicencee );
		$edd_osgiLicenceMetadataStr = ( string ) $osgilicenceMetadata->asXML ();

		update_post_meta ( $post_id, 'edd_osgiLicenceMetadataStr', $edd_osgiLicenceMetadataStr );
	} elseif ($osgipub_osgi_debug == 'on')
		echo "not first time page has been viewed so edd_osgiLicenceMetadataStr is already populated\n";
		
		// parse the licence matadata we have just saved as a string
	$osgilicenceMetadata = new SimpleXMLElement ( $edd_osgiLicenceMetadataStr );
	
	// check if the page post is telling us to generate a licence
	if (isset ( $_POST ['generateLicence'] )) {
		if ($osgipub_osgi_debug == 'on')
			echo "Generate licence button pressed. Generating new licence.\n";
		$edd_osgiLicenceStr = ( string ) generateLicence ( $osgilicenceMetadataSpec, $osgilicenceMetadata, $osgiLicenceGeneratorUrl, $osgi_username, $osgi_password, $osgipub_osgi_debug, $post_id );
		update_post_meta ( $post_id, 'edd_osgiLicenceStr', $edd_osgiLicenceStr );
	}
	?>
<?php if( $osgipub_osgi_debug=='on') { ?>
</textarea>
<?php
	}
	?>
<h1>Generate OSGi Licence</h1>

<?php 
if(isset($edd_payment_post_id)){
?>
<p><a href="<?php echo add_query_arg( 'payment_key', edd_get_payment_key( $edd_payment_post_id ), edd_get_success_page_uri() );?>" >Link to Payment Receipt: <?php echo edd_get_payment_number( $edd_payment_post_id ); ?></a><p>
<?php 
}
?>

<h2>Licence Metadata</h2>

<form method="post" action="" enctype="multipart/form-data">
<?php echo licenceMetadataForm($osgilicenceMetadata, $osgilicenceMetadataSpec, $noEditMetadata)?>
<?php if ($noEditMetadata==TRUE) { ?>
<p>(Cannot be Edited)</p>
<?php } else { ?>
<p>(Empty fields marked with ** must be completed before generating a
		licence)"</p>
<?php } ?>
<p></p>
<?php if (isset($edd_osgiLicenceStr) && $edd_osgiLicenceStr!='' ) { ?>
	<h2>Generated licence</h2>
	<p></p>
	<textarea name="edd_osgiLicenceStr" rows="10" cols="50"
		style="width: 100%; border: 3px solid;" readonly><?php echo $edd_osgiLicenceStr; ?></textarea>
	<p>(Copy and past this licence into your system)</p>
	<input type="hidden" name="resetLicence" value="true">
	<p>
		<button type="submit">Reset Licence</button>
		Clears the licence and allows the licence metadata to be edited.
	</p>
<?php } else { ?>
	<input type="hidden" name="generateLicence" value="true">
	<p>
		<button type="submit">Generate Licence</button>
		Generates a new licence String from licence metadata.
	</p>

<?php } ?>
</form>
<form method="post" action="" enctype="multipart/form-data">
	<input type="hidden" name="resetLicenceSpec" value="true">
	<button type="submit">Reset Licence Specification</button>
	Clears the licence and resets the licence metadata to the default
	specification from the licence manager and allows editing.<BR>
</form>
<?php
} catch ( Exception $e ) {
	echo 'Problem loading page: Exception: ', $e->getMessage (), "\n";
}
?>

<?php get_sidebar(); ?>
<?php get_footer(); ?>