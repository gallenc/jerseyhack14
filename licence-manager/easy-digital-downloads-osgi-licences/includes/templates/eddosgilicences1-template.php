<?php
/**
 * The template for displaying licence info.
 * uses httpful.phar library
*/
get_header(); 
?>

<?php
echo 'xml test page';

$uri = 'http://localhost:8181/licencemgr/rest/licence-pub/getlicencemetadataspec?productId=org.opennms.co.uk/org.opennms.co.uk.newfeature/0.0.1-SNAPSHOT';
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