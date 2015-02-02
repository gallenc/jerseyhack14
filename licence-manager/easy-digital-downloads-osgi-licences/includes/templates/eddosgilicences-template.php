<?php
/**
 * The template for displaying licence info.
 * uses httpful.phar library
 * 
 * Usecase
 * 
 * Data used by page
 * 1. metadata osgiLicenceStr contains licence string calculatedc from liceme metadataspec
 * 2. metadata osgiLicenceMetadata contains spec and/or completed licence metadata
 *        osgiLicenceMetaDataSpec XML representation of the specification for licence
 *        osgiLicenceMetadata XML represenation of the actual metadata used to construct licence
 *          (this is constructed from the licenceMetaDataSpec, user input and user account data).
 * 4. metadata osgiLicencee username of customer who can view and modify this licence
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
 *       osgiLicencee username of customer who can view and modify this licence
 *       osgiProductId maven unique id of purchased product to which this licence applies
 *       retrieve  osgiLicenceMetadataSpec for productId from Karaf licence manager.
 *       populate  osgiLicenceMetadataSpec with osgiLicencee data
 *    [if not fail and do not process transaction]
 * 3. post purchase create new licence post/page with 
 *       publish page and redirect user to page
 * 4. user views page and generates licence at will
 *   (each purhased licence references PO and PO references licence page)
 *   page has [create licence] button and possibly ( property defined ) reset button to regenerate
 * 
 * THIS IS LICENCE PAGE TEMPLATE for creating a licence
*/
get_header(); 
?>

<?php
echo 'xml test page';

$post_id = get_the_ID();
echo 'post id=' . $post_id ."\n<BR>";

// contains  maven unique id of product to which this licence applies
$osgiProductId=update_post_meta( $post_id, 'osgiProductId', 'org.opennms.co.uk/org.opennms.co.uk.newfeature/0.0.1-SNAPSHOT' );
//  contains licence string calculatedc from liceme metadataspec
// $osgiLicenceStr=update_post_meta( $post_id, 'osgiLicenceStr', true );
// //  contains spec and/or completed licence metadata
// $osgiLicenceMetadata=update_post_meta( $post_id, 'osgiLicenceMetadata', true );
// // contains XML representation of the specification for licence
// $osgiLicenceMetaDataSpec=update_post_meta( $post_id, 'osgiLicenceMetaDataSpec', true );
// // contains username of customer who can view and modify this licence
// $osgiLicencee=update_post_meta( $post_id, 'osgiLicencee', true );

//add_post_meta( $post_ID, 'enclosure', "$url\n$len\n$mime\n" );
//	$backup_sizes = get_post_meta( $post_id, '_wp_attachment_backup_sizes', true );
// update_post_meta( $post_id, '_wp_attachment_backup_sizes', true );

//if ( isset( $_POST['post_ID'] ) )
//						$post_id = (int) $_POST['post_ID'];
// // Show post form.
//$post = get_default_post_to_edit( $post_type, true );
//$post_ID = $post->ID;


// contains  maven unique id of product to which this licence applies
$osgiProductId=get_post_meta( $post_id, 'osgiProductId', true );
//  contains licence string calculatedc from liceme metadataspec
$osgiLicenceStr=get_post_meta( $post_id, 'osgiLicenceStr', true );
//  contains spec and/or completed licence metadata
$osgiLicenceMetadata=get_post_meta( $post_id, 'osgiLicenceMetadata', true );

// contains XML representation of the specification for licence
$osgiLicenceMetaDataSpec=get_post_meta( $post_id, 'osgiLicenceMetaDataSpec', true );

// contains username of customer who can view and modify this licence
$osgiLicencee=get_post_meta( $post_id, 'osgiLicencee', true );


$uri = 'http://localhost:8181/pluginmgr/rest/licence-pub/getlicencemetadataspec?productId=org.opennms.co.uk/org.opennms.co.uk.newfeature/0.0.1-SNAPSHOT';
$response = \Httpful\Request::get ( $uri )->expectsXml()->send();

echo "debug data";
echo "response code \n<BR>";
echo "{$response->code}";
echo "\n<BR>";

echo "response type \n<BR>";
echo "{$response->content_type}";
echo "\n<BR>";

echo "has errors \n<BR>";
echo "{$response->hasErrors()}";
echo "\n<BR>";

echo "raw body var dump \n<BR>";
echo "{$response->raw_body}";
echo "\n<BR>";

echo "body var dump \n<BR>";
var_dump ( $response->body );
echo "\n<BR>";

echo "licenceMetadata vardump\n<BR>";
var_dump ($response->body->licenceMetadata);
echo "\n<BR>";

$licenceMetadata=$response->body->licenceMetadata;

echo " product id {$licenceMetadata->productId}";
echo "\n<BR>";
echo " licensee {$licenceMetadata->licensee}";

echo "licenceMetadata iteration \n<BR>";
foreach($licenceMetadata->children() as  $key => $value) {
	echo $key . "=" . $value;
	echo "\n<BR>";
}

echo "end licenceMetadata iteration\n<BR>";

echo "licenceMetadata->asXML \n<BR>";
//echo "{$licenceMetadata->asXML()}";

$licenceMetadataPropertyStr="![CDATA[" . $licenceMetadata->asXML() ."]]";
echo "$licenceMetadataPropertyStr=" . $licenceMetadataPropertyStr;

echo "\n<BR>";

echo "end debug data";



	
?>
<textarea name="licenceMetadata"><?php echo $licenceMetadataPropertyStr; ?></textarea>

<?php 
foreach($licenceMetadata->children() as  $key => $value) {
	if ($key != "options") {
?>
<?php echo $key; ?> <input type="text" name="<?php echo $key; ?>" value="<?php echo $value; ?>" <?php if($value!== '') echo " readonly"; ?> ><br>
<?php
	}
}
?>



<?php get_sidebar(); ?>
<?php get_footer(); ?>